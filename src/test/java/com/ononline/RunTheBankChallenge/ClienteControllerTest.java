package com.ononline.RunTheBankChallenge;

import com.ononline.RunTheBankChallenge.Controllers.ClienteController;
import com.ononline.RunTheBankChallenge.Data.Entities.Cliente;
import com.ononline.RunTheBankChallenge.Data.Repositories.ClienteRepository;
import com.ononline.RunTheBankChallenge.Exceptions.ClienteAlreadyExistsException;
import com.ononline.RunTheBankChallenge.Exceptions.ClienteNotFoundException;
import com.ononline.RunTheBankChallenge.Exceptions.InvalidCpfCnpjException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Testes unitários para a classe ClienteController.
 */
@SpringBootTest
public class ClienteControllerTest {
    
    @Mock
    private ClienteRepository clienteRepository;
    
    @InjectMocks
    private ClienteController clienteController;
    
    /**
     * Testa o método postCliente da classe ClienteController.
     */
    @Test
    public void testPostCliente() {
        // Criação de dados
        Cliente novoCliente = new Cliente();
        novoCliente.setNome("Neil Gaiman");
        novoCliente.setCpfCnpj("34379117081");
        novoCliente.setEndereco("Rua do Oceano, 99");
        novoCliente.setSenha("SuperSenhaSecreta");
        
        // Repositórios mockados
        when(clienteRepository.findByCpfCnpj(novoCliente.getCpfCnpj())).thenReturn(new ArrayList<>());
        when(clienteRepository.save(any(Cliente.class))).thenReturn(novoCliente);
        
        // Teste envio correto CPF
        ResponseEntity<Cliente> responseEntity = clienteController.postCliente(novoCliente);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(novoCliente, responseEntity.getBody());
        
        // Teste envio correto CNPJ
        novoCliente.setCpfCnpj("30230334000112");
        responseEntity = clienteController.postCliente(novoCliente);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(novoCliente, responseEntity.getBody());
        
        // Teste cliente duplicado
        when(clienteRepository.findByCpfCnpj(novoCliente.getCpfCnpj())).thenReturn(Collections.singletonList(novoCliente));
        assertThrows(ClienteAlreadyExistsException.class, () -> clienteController.postCliente(novoCliente));
        
        // Teste CPF/CNPJ inválido
        novoCliente.setCpfCnpj("12345678901234"); // An invalid length
        assertThrows(InvalidCpfCnpjException.class, () -> clienteController.postCliente(novoCliente));
    }
    
    /**
     * Testa o método getCliente da classe ClienteController.
     */
    @Test
    public void testGetCliente() {
        // Criação de Dados
        Long clienteIdFound = 1L;
        Long clienteIdNotFound = 300L;
        Cliente expectedCliente = new Cliente();
        expectedCliente.setId(clienteIdFound);
        expectedCliente.setNome("Oratrice Mecanique D'analyse Cardinale");
        expectedCliente.setCpfCnpj("82409519539");
        expectedCliente.setEndereco("Estrada da Perdição, 99");
        expectedCliente.setSenha("senhaSecreta");
        
        // Repositórios mockados
        when(clienteRepository.findById(clienteIdFound)).thenReturn(Optional.of(expectedCliente));
        
        // Teste dados recuperados
        ResponseEntity<Cliente> responseEntity = clienteController.getCliente(clienteIdFound);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedCliente, responseEntity.getBody());
        
        // Teste dados inexistentes
        assertThrows(ClienteNotFoundException.class, () -> clienteController.getCliente(clienteIdNotFound));
    }
}
