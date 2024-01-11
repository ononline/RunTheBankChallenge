package com.ononline.RunTheBankChallenge.Controllers;

import com.ononline.RunTheBankChallenge.Data.Entities.Cliente;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {

    //TODO: Criar método real

    @GetMapping("/cliente")
    public Cliente getCliente(){
        return new Cliente(12345L,
                "Tiago Monteiro",
                "123456789",
                "Rua A",
                "senhaSecreta");
    }
}
