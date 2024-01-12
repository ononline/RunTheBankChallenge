package com.ononline.RunTheBankChallenge.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.UNPROCESSABLE_ENTITY, reason="Valor da transação inválido")
public class InvalidTransactionValueException extends RuntimeException{
    public InvalidTransactionValueException(){
        super("Transação com valor inválido");
    }
    
    public InvalidTransactionValueException(String message){
        super(message);
    }
}
