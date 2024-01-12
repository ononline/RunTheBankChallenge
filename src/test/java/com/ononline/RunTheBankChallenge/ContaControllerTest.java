package com.ononline.RunTheBankChallenge;

import com.ononline.RunTheBankChallenge.Controllers.ContaController;
import com.ononline.RunTheBankChallenge.Data.Entities.Cliente;
import com.ononline.RunTheBankChallenge.Data.Entities.Conta;
import com.ononline.RunTheBankChallenge.Data.Ids.ContaId;
import com.ononline.RunTheBankChallenge.Data.Repositories.ClienteRepository;
import com.ononline.RunTheBankChallenge.Data.Repositories.ContaRepository;
import com.ononline.RunTheBankChallenge.Data.Repositories.TransacaoRepository;
import com.ononline.RunTheBankChallenge.Exceptions.ClienteNotFoundException;
import com.ononline.RunTheBankChallenge.Exceptions.ContaAlreadyExistsException;
import com.ononline.RunTheBankChallenge.Exceptions.ContaNotFoundException;
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
}
