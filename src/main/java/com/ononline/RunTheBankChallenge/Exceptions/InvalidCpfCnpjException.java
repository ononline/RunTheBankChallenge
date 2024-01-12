package com.ononline.RunTheBankChallenge.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando um CPF ou CNPJ é considerado inválido.
 */
@ResponseStatus(value=HttpStatus.UNPROCESSABLE_ENTITY, reason="CPF ou CNPJ inválido")
public class InvalidCpfCnpjException extends RuntimeException{
    public InvalidCpfCnpjException(){
        super("CPF ou CNPJ inválido");
    }
    
    public InvalidCpfCnpjException(String message){
        super(message);
    }
}
