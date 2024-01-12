package com.ononline.RunTheBankChallenge;

import com.ononline.RunTheBankChallenge.Controllers.ContaController;
import com.ononline.RunTheBankChallenge.Data.Entities.Cliente;
import com.ononline.RunTheBankChallenge.Data.Entities.Conta;
import com.ononline.RunTheBankChallenge.Data.Entities.Transacao;
import com.ononline.RunTheBankChallenge.Data.Entities.TransacaoSDO;
import com.ononline.RunTheBankChallenge.Data.Ids.ContaId;
import com.ononline.RunTheBankChallenge.Data.Repositories.ClienteRepository;
import com.ononline.RunTheBankChallenge.Data.Repositories.ContaRepository;
import com.ononline.RunTheBankChallenge.Data.Repositories.TransacaoRepository;
import com.ononline.RunTheBankChallenge.Exceptions.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ContaControllerTest {
    
    @Mock
    private ContaRepository contaRepository;
    
    @Mock
    private ClienteRepository clienteRepository;
    
    @Mock
    private TransacaoRepository transacaoRepository;
    
    @InjectMocks
    private ContaController contaController;
    
    @Test
    public void testPostConta() {
        // Criação de dados
        long clienteId = 1L;
        int agencia = 1234;
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setNome("Neil Gaiman");
        cliente.setCpfCnpj("34379117081");
        cliente.setEndereco("Rua do Oceano, 99");
        cliente.setSenha("SuperSenhaSecreta");
        
        Conta novaConta = new Conta();
        novaConta.setId(1L);
        novaConta.setCliente(cliente);
        novaConta.setAgencia(agencia);
        novaConta.setSaldo(100.0f);
        novaConta.setStatus(true);
        
        // Repositórios mockados
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(contaRepository.findById(new ContaId(novaConta.getId(), novaConta.getAgencia()))).thenReturn(Optional.empty());
        when(contaRepository.save(any(Conta.class))).thenReturn(novaConta);
        
        // Teste envio de conta válida
        ResponseEntity<Conta> responseEntity = contaController.postConta(novaConta);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(novaConta, responseEntity.getBody());
        
        // Teste envio de conta duplicada
        when(contaRepository.findById(new ContaId(novaConta.getId(), novaConta.getAgencia()))).thenReturn(Optional.of(novaConta));
        assertThrows(ContaAlreadyExistsException.class, () -> contaController.postConta(novaConta));
        
        // Teste envio de conta com cliente inválido
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());
        assertThrows(ClienteNotFoundException.class, () -> contaController.postConta(novaConta));
        
    }
    
    @Test
    public void testInactivateConta() {
        // Criação de dados
        long clienteId = 1L;
        int agencia = 1234;
        long contaId = 1L;
        
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setNome("John Doe");
        cliente.setCpfCnpj("39828330865");
        cliente.setEndereco("123 Main St");
        cliente.setSenha("password");
        
        Conta conta = new Conta();
        conta.setId(contaId);
        conta.setCliente(cliente);
        conta.setAgencia(agencia);
        conta.setSaldo(100.0f);
        conta.setStatus(true);
        
        // Repositórios mockados
        when(contaRepository.findById(new ContaId(contaId, agencia))).thenReturn(Optional.of(conta));
        when(contaRepository.save(any(Conta.class))).thenReturn(conta);
        
        // Teste invalida conta com sucesso
        ResponseEntity<Boolean> responseEntity = contaController.inactivateConta(contaId, agencia);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Boolean.TRUE, responseEntity.getBody());
        assertFalse(conta.isStatus());
        
        // Teste conta inexistente
        when(contaRepository.findById(new ContaId(contaId, agencia))).thenReturn(Optional.empty());
        assertThrows(ContaNotFoundException.class, () -> contaController.inactivateConta(contaId, agencia));
    }
    
    @Test
    public void testGetConta() {
        // Criação de dados
        Long clienteId = 1L;
        int agencia = 1234;
        long contaId = 1L;
        
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setNome("John Doe");
        cliente.setCpfCnpj("12345678901");
        cliente.setEndereco("123 Main St");
        cliente.setSenha("password");
        
        Conta expectedConta = new Conta();
        expectedConta.setId(contaId);
        expectedConta.setCliente(cliente);
        expectedConta.setAgencia(agencia);
        expectedConta.setSaldo(100.0f);
        expectedConta.setStatus(true);
        
        // Repositórios mockados
        when(contaRepository.findById(new ContaId(contaId, agencia))).thenReturn(Optional.of(expectedConta));
        
        // Teste recupera conta com sucesso
        ResponseEntity<Conta> responseEntity = contaController.getConta(contaId, agencia);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedConta, responseEntity.getBody());
        
        // Teste conta não encontrada
        when(contaRepository.findById(new ContaId(contaId, agencia))).thenReturn(Optional.empty());
        assertThrows(ContaNotFoundException.class, () -> contaController.getConta(contaId, agencia));
        
    }
    
    @Test
    public void testPostTransacao() {
        // Criação de dados
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("John Doe");
        cliente.setCpfCnpj("12345678901");
        cliente.setEndereco("123 Main St");
        cliente.setSenha("password");
        
        ContaId contaIdOrigem = new ContaId(1L, 1234);
        Conta contaOrigem = new Conta();
        contaOrigem.setId(contaIdOrigem.getId());
        contaOrigem.setCliente(cliente);
        contaOrigem.setAgencia(contaIdOrigem.getAgencia());
        contaOrigem.setSaldo(200.0f);
        contaOrigem.setStatus(true);
        
        ContaId contaIdDestino = new ContaId(1L, 1234);
        Conta contaDestino = new Conta();
        contaDestino.setId(contaIdDestino.getId());
        contaDestino.setCliente(cliente);
        contaDestino.setAgencia(contaIdDestino.getAgencia());
        contaDestino.setSaldo(200.0f);
        contaDestino.setStatus(true);
        
        TransacaoSDO transacaoSDO = new TransacaoSDO();
        transacaoSDO.setContaOrigem(contaIdOrigem);
        transacaoSDO.setContaDestino(contaIdDestino);
        transacaoSDO.setValor(150.00F);
        
        Transacao transacao = new Transacao();
        transacao.setId(1L);
        transacao.setValor(transacaoSDO.getValor());
        transacao.setContaOrigem(contaOrigem);
        transacao.setContaDestino(contaDestino);
        transacao.setRevertido(false);
        transacao.setObservacoes("");
        
        // Repositórios mockados
        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacao);
        when(contaRepository.findById(contaIdOrigem)).thenReturn(Optional.of(contaOrigem));
        when(contaRepository.findById(contaIdDestino)).thenReturn(Optional.of(contaDestino));
        when(contaRepository.save(contaOrigem)).thenReturn(contaOrigem);
        when(contaRepository.save(contaDestino)).thenReturn(contaDestino);
        
        // Teste transação executada com sucesso
        ResponseEntity<Transacao> responseEntity = contaController.postTransacao(transacaoSDO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(transacao, responseEntity.getBody());
        
        // Teste valor inválido
        transacaoSDO.setValor(-100);
        assertThrows(InvalidTransactionValueException.class, () -> contaController.postTransacao(transacaoSDO));
        
        // Teste conta com saldo abaixo do necessário
        transacaoSDO.setValor(300);
        assertThrows(NotEnoughSaldoException.class, () -> contaController.postTransacao(transacaoSDO));
        transacaoSDO.setValor(150);
        
        // Teste conta destino inativa
        contaDestino.setStatus(false);
        assertThrows(ContaNotFoundException.class, () -> contaController.postTransacao(transacaoSDO));
        
        // Teste conta destino inválida
        contaIdDestino.setId(30L);
        transacaoSDO.setContaDestino(contaIdDestino);
        assertThrows(ContaNotFoundException.class, () -> contaController.postTransacao(transacaoSDO));
        
        // Teste conta origem inativa
        contaOrigem.setStatus(false);
        assertThrows(ContaNotFoundException.class, () -> contaController.postTransacao(transacaoSDO));
        
        // Teste conta origem inválida
        contaIdOrigem.setId(30L);
        transacaoSDO.setContaOrigem(contaIdOrigem);
        assertThrows(ContaNotFoundException.class, () -> contaController.postTransacao(transacaoSDO));
    }
    
    @Test
    public void testDeleteTransacao() {
        // Criação de dados
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("John Doe");
        cliente.setCpfCnpj("12345678901");
        cliente.setEndereco("123 Main St");
        cliente.setSenha("password");
        
        ContaId contaIdOrigem = new ContaId(1L, 1234);
        Conta contaOrigem = new Conta();
        contaOrigem.setId(contaIdOrigem.getId());
        contaOrigem.setCliente(cliente);
        contaOrigem.setAgencia(contaIdOrigem.getAgencia());
        contaOrigem.setSaldo(200.0f);
        contaOrigem.setStatus(true);
        
        ContaId contaIdDestino = new ContaId(1L, 1234);
        Conta contaDestino = new Conta();
        contaDestino.setId(contaIdDestino.getId());
        contaDestino.setCliente(cliente);
        contaDestino.setAgencia(contaIdDestino.getAgencia());
        contaDestino.setSaldo(200.0f);
        contaDestino.setStatus(true);
        
        TransacaoSDO transacaoSDO = new TransacaoSDO();
        transacaoSDO.setContaOrigem(contaIdOrigem);
        transacaoSDO.setContaDestino(contaIdDestino);
        transacaoSDO.setValor(150.00F);
        
        long idTransacao = 1L;
        Transacao transacao = new Transacao();
        transacao.setId(idTransacao);
        transacao.setValor(transacaoSDO.getValor());
        transacao.setContaOrigem(contaOrigem);
        transacao.setContaDestino(contaDestino);
        transacao.setRevertido(false);
        transacao.setObservacoes("");
        
        // Repositórios mockados
        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacao);
        when(transacaoRepository.findById(idTransacao)).thenReturn(Optional.of(transacao));
        when(contaRepository.findById(contaIdOrigem)).thenReturn(Optional.of(contaOrigem));
        when(contaRepository.findById(contaIdDestino)).thenReturn(Optional.of(contaDestino));
        when(contaRepository.save(contaOrigem)).thenReturn(contaOrigem);
        when(contaRepository.save(contaDestino)).thenReturn(contaDestino);
        
        // Teste transação revertida com sucesso
        ResponseEntity<Boolean> responseEntity = contaController.reverteTransacao(idTransacao);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Boolean.TRUE, responseEntity.getBody());
        
        // Teste transação revertida anteriormente
        assertThrows(InvalidTransactionException.class,  () -> contaController.reverteTransacao(idTransacao));
        transacao.setRevertido(false);
        
        // Teste transação não encontrada
        assertThrows(TransactionNotFoundException.class, () -> contaController.reverteTransacao(5L));
        
        // Teste conta com saldo abaixo do necessário
        contaDestino.setSaldo(50f);
        assertThrows(NotEnoughSaldoException.class, () -> contaController.reverteTransacao(idTransacao));
        
        // Teste conta destino inativa
        contaDestino.setStatus(false);
        assertThrows(ContaNotFoundException.class, () -> contaController.postTransacao(transacaoSDO));
        
        // Teste conta destino inválida
        contaIdDestino.setId(30L);
        transacaoSDO.setContaDestino(contaIdDestino);
        assertThrows(ContaNotFoundException.class, () -> contaController.postTransacao(transacaoSDO));
        
        // Teste conta origem inativa
        contaOrigem.setStatus(false);
        assertThrows(ContaNotFoundException.class, () -> contaController.postTransacao(transacaoSDO));
        
        // Teste conta origem inválida
        contaIdOrigem.setId(30L);
        transacaoSDO.setContaOrigem(contaIdOrigem);
        assertThrows(ContaNotFoundException.class, () -> contaController.postTransacao(transacaoSDO));
    }
}
