package com.ononline.RunTheBankChallenge.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.UNPROCESSABLE_ENTITY, reason="Transação inválida")
public class InvalidTransactionException extends RuntimeException{
    public InvalidTransactionException(){
        super("Transação inválida");
    }
    
    public InvalidTransactionException(String message){
        super(message);
    }
}
