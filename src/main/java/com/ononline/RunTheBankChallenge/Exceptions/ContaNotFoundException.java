package com.ononline.RunTheBankChallenge.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando uma conta não é encontrada na base de dados.
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Conta não encontrada na base")
public class ContaNotFoundException extends RuntimeException{
    public ContaNotFoundException() {
        super("Conta não encontrada");
    }
    
    public ContaNotFoundException(String message) {
        super(message);
    }
}
