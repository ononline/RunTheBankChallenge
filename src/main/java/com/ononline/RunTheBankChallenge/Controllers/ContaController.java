package com.ononline.RunTheBankChallenge.Controllers;

import com.ononline.RunTheBankChallenge.Data.Entities.Conta;
import com.ononline.RunTheBankChallenge.Data.Entities.Transacao;
import com.ononline.RunTheBankChallenge.Data.Entities.TransacaoSDO;
import com.ononline.RunTheBankChallenge.Data.Ids.ContaId;
import com.ononline.RunTheBankChallenge.Data.Repositories.ClienteRepository;
import com.ononline.RunTheBankChallenge.Data.Repositories.ContaRepository;
import com.ononline.RunTheBankChallenge.Data.Repositories.TransacaoRepository;
import com.ononline.RunTheBankChallenge.Exceptions.*;
import com.ononline.RunTheBankChallenge.Utilities.SendNotification;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador responsável por lidar com operações relacionadas a contas bancárias.
 */
@RestController
@AllArgsConstructor
public class ContaController {
    
    private ContaRepository contaRepository;
    private ClienteRepository clienteRepository;
    private TransacaoRepository transacaoRepository;
    
    /**
     * Registra uma nova conta no sistema.
     * @param novaConta A conta a ser registrada.
     * @return Um ResponseEntity<Conta> contendo a conta recém-registrada.
     *         Retorna HttpStatus.CREATED com a conta, ou HttpStatus.CONFLICT se a conta já existir na base de dados.
     * @throws ClienteNotFoundException Lançada se o cliente associado à conta não for encontrado na base de dados.
     */
    @PostMapping("/conta")
    public ResponseEntity<Conta> postConta(@RequestBody Conta novaConta){
        if(clienteRepository.findById(novaConta.getCliente().getId()).isEmpty())
            throw new ClienteNotFoundException();
        if (contaRepository.findById(new ContaId(novaConta.getId(), novaConta.getAgencia())).isEmpty()) {
            Conta savedConta = contaRepository.save(novaConta);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedConta);
        } else {
            throw new ContaAlreadyExistsException();
        }
    }
    
    
    /**
     * Inativa uma conta bancária com base no ID e na agência.
     * @param idConta O ID da conta a ser inativada.
     * @param agencia A agência da conta a ser inativada.
     * @return Um ResponseEntity<Boolean> indicando o resultado da inativação da conta.
     *         Retorna HttpStatus.OK com true se a conta foi inativada com sucesso,
     *         ou HttpStatus.NOT_FOUND caso a conta não seja encontrada.
     */
    @DeleteMapping("/conta/{agencia}/{id}")
    public ResponseEntity<Boolean> inactivateConta(@PathVariable("id") Long idConta, @PathVariable("agencia") Integer agencia){
        Conta conta = contaRepository.findById(new ContaId(idConta, agencia)).orElseThrow(ContaNotFoundException::new);
        conta.setStatus(false);
        contaRepository.save(conta);
        return ResponseEntity.ok(Boolean.TRUE);
    }
    
    /**
     * Obtém as informações de uma conta bancária com base no ID e na agência.
     * @param idConta O ID da conta a ser recuperada.
     * @param agencia A agência da conta a ser recuperada.
     * @return Um ResponseEntity<Conta> contendo a conta correspondente ao ID e à agência fornecidos.
     *         Retorna HttpStatus.OK com a conta, ou HttpStatus.NOT_FOUND caso a conta não seja encontrada.
     */
    @GetMapping("/conta/{agencia}/{id}")
    public ResponseEntity<Conta> getConta(@PathVariable("id") Long idConta, @PathVariable("agencia") Integer agencia){
        Conta conta = contaRepository.findById(new ContaId(idConta, agencia)).orElseThrow(ContaNotFoundException::new);
        return ResponseEntity.ok(conta);
    }
    
    /**
     * Realiza uma transferência de valor entre as contas indicadas.
     * @param novaTransacao Objeto contendo os IDs das contas origem e destino e o valor da transação.
     * @return Um ResponseEntity<Transacao> contendo todos os dados da transação realizada.
     *         Retorna HttpStatus.OK com a transação, ou HttpStatus.NOT_FOUND caso alguma conta não seja encontrada.
     * @throws InvalidTransactionValueException Lançada se o valor da transação for menor ou igual a zero.
     * @throws ContaNotFoundException Lançada se alguma das contas não for encontrada na base de dados ou estiver inativa.
     * @throws NotEnoughSaldoException Lançada se a conta de origem não tiver saldo suficiente para realizar a transação.
     */
    @PostMapping("/transacao")
    public ResponseEntity<Transacao> postTransacao(@RequestBody TransacaoSDO novaTransacao) {
        // Valida valor negativo
        if (novaTransacao.getValor() <= 0) {
            throw new InvalidTransactionValueException("Valor da transação deve ser maior que zero.");
        }
        
        // Obtem a conta de origem e valida status
        Conta contaOrigem = getContaOrThrowExceptionAndCheckStatus(novaTransacao.getContaOrigem());
        
        // Valida saldo da conta origem
        if (contaOrigem.getSaldo() < novaTransacao.getValor()) {
            throw new NotEnoughSaldoException("Saldo insuficiente na conta de origem.");
        }
        
        // Obtem a conta de destino e valida status
        Conta contaDestino = getContaOrThrowExceptionAndCheckStatus(novaTransacao.getContaDestino());
        
        // Realiza débito e crédito nas contas
        contaOrigem.debit(novaTransacao.getValor());
        contaDestino.credit(novaTransacao.getValor());
        
        // Registra a transação
        Transacao transacaoRealizada = transacaoRepository.save(
                new Transacao(0L, contaOrigem, contaDestino, novaTransacao.getValor(), false, ""));
        
        // Notifica os envolvidos
        SendNotification.ofDebit(transacaoRealizada);
        SendNotification.ofCredit(transacaoRealizada);
        
        return ResponseEntity.ok(transacaoRealizada);
    }
    
    /**
     * Obtém uma conta com base no ID fornecido e lança uma exceção se a conta não for encontrada
     * ou estiver inativa, utilizando o método auxiliar checkContaStatus para verificar o status.
     * @param contaId O ID da conta a ser obtida.
     * @return A conta correspondente ao ID fornecido, após verificar e garantir que esteja ativa.
     * @throws ContaNotFoundException Lançada se a conta não for encontrada ou estiver inativa.
     */
    private Conta getContaOrThrowExceptionAndCheckStatus(ContaId contaId) {
        return contaRepository.findById(contaId)
                .map(this::checkContaStatus)
                .orElseThrow(() -> new ContaNotFoundException("Conta não encontrada para o ID: " + contaId));
    }
    
    /**
     * Verifica o status de uma conta e lança uma exceção se a conta estiver inativa.
     * @param conta A conta a ser verificada.
     * @return A conta, se estiver ativa.
     * @throws ContaNotFoundException Lançada se a conta estiver inativa.
     */
    private Conta checkContaStatus(Conta conta) {
        if (!conta.isStatus()) {
            throw new ContaNotFoundException("Conta inativa para o ID: " + conta.getId());
        }
        return conta;
    }
    
    /**
     * Reverte uma transação com base no ID fornecido.
     * @param idTransacao O ID da transação a ser revertida.
     * @return Um ResponseEntity<Boolean> indicando se a transação foi revertida com sucesso.
     *         Retorna HttpStatus.OK com true se a transação foi revertida,
     *         ou HttpStatus.NOT_FOUND se a transação não for encontrada ou já tiver sido revertida.
     * @throws TransactionNotFoundException Lançada se a transação não for encontrada na base de dados.
     * @throws InvalidTransactionException Lançada se a transação já tiver sido revertida anteriormente.
     * @throws ContaNotFoundException Lançada se alguma das contas associadas à transação não for encontrada na base de dados ou estiver inativa.
     * @throws NotEnoughSaldoException Lançada se a conta de destino não tiver saldo suficiente para reverter a transação.
     */
    @DeleteMapping("/transacao/{id}")
    public ResponseEntity<Boolean> reverteTransacao(@PathVariable("id") Long idTransacao){
        
        // Obtem todos dados necessários do banco de dados.
        Transacao transacao = transacaoRepository.findById(idTransacao).orElseThrow(TransactionNotFoundException::new);
        if (transacao.isRevertido())
            throw new InvalidTransactionException();
        
        ContaId contaOrigemId = new ContaId(transacao.getContaOrigem().getId(), transacao.getContaOrigem().getAgencia());
        Conta contaOrigem = contaRepository.findById(contaOrigemId).orElseThrow(ContaNotFoundException::new);
        if (!contaOrigem.isStatus())
            throw new ContaNotFoundException();
        ContaId contaDestinoId = new ContaId(transacao.getContaDestino().getId(), transacao.getContaDestino().getAgencia());
        Conta contaDestino = contaRepository.findById(contaDestinoId).orElseThrow(ContaNotFoundException::new);
        if (!contaDestino.isStatus())
            throw new ContaNotFoundException();
        if (contaDestino.getSaldo() < transacao.getValor())
            throw new NotEnoughSaldoException();
        
        // Realiza débito e crédito nas contas
        contaOrigem.credit(transacao.getValor());
        contaDestino.debit(transacao.getValor());
        
        // Registra a transação
        transacao.setRevertido(true);
        Transacao transacaoRealizada = transacaoRepository.save(transacao);
        
        // Notifica os envolvidos
        SendNotification.ofDebit(transacaoRealizada);
        SendNotification.ofCredit(transacaoRealizada);
        
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
