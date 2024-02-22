package com.ononline.RunTheBankChallenge.Controllers;

import com.ononline.RunTheBankChallenge.Data.Entities.Cliente;
import com.ononline.RunTheBankChallenge.Data.Repositories.ClienteRepository;
import com.ononline.RunTheBankChallenge.Exceptions.ClienteAlreadyExistsException;
import com.ononline.RunTheBankChallenge.Exceptions.ClienteNotFoundException;
import com.ononline.RunTheBankChallenge.Utilities.CpfCnpjUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador responsável por lidar com operações relacionadas a clientes.
 */
@RestController
public class ClienteController {
    
    private final ClienteRepository clienteRepository;
    
    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    
    /**
     * Registra um novo cliente no sistema.
     *
     * @param novoCliente O cliente a ser registrado.
     * @return O cliente recém-registrado.
     * @throws ClienteAlreadyExistsException Lançada se o cliente com o mesmo CPF/CNPJ já existe.
     */
    @PostMapping("/cliente")
    public ResponseEntity<Cliente> postCliente(@RequestBody Cliente novoCliente) {
        CpfCnpjUtil.valida(novoCliente.getCpfCnpj());
        if (clienteRepository.findByCpfCnpj(novoCliente.getCpfCnpj()).isEmpty()) {
            Cliente savedCliente = clienteRepository.save(novoCliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCliente);
        } else {
            throw new ClienteAlreadyExistsException();
        }
    }
    
    /**
     * Obtém as informações de um cliente com base no ID.
     *
     * @param id O ID do cliente a ser recuperado.
     * @return O cliente correspondente ao ID fornecido.
     * @throws ClienteNotFoundException Se o cliente com o ID fornecido não for encontrado.
     */
    @GetMapping("/cliente/{id}")
    public ResponseEntity<Cliente> getCliente(@PathVariable("id") Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(ClienteNotFoundException::new);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }
}
