package com.ononline.RunTheBankChallenge.Controllers;

import com.ononline.RunTheBankChallenge.Data.Entities.Conta;
import com.ononline.RunTheBankChallenge.Data.Ids.ContaId;
import com.ononline.RunTheBankChallenge.Data.Repositories.ClienteRepository;
import com.ononline.RunTheBankChallenge.Data.Repositories.ContaRepository;
import com.ononline.RunTheBankChallenge.Data.Repositories.TransacaoRepository;
import com.ononline.RunTheBankChallenge.Exceptions.ClienteNotFoundException;
import com.ononline.RunTheBankChallenge.Exceptions.ContaAlreadyExistsException;
import com.ononline.RunTheBankChallenge.Exceptions.ContaNotFoundException;
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
    
}
