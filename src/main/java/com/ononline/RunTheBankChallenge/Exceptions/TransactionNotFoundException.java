package com.ononline.RunTheBankChallenge.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Transação não encontrada")
public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException(){
        super("Transação não encontrada");
    }
    
    public TransactionNotFoundException(String message){
        super(message);
    }
}
