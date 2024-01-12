package com.ononline.RunTheBankChallenge.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="Saldo da conta insuficiente")
public class NotEnoughSaldoException extends RuntimeException{
    public NotEnoughSaldoException(){
        super("Saldo insuficiente");
    }
    
    public NotEnoughSaldoException(String message){
        super(message);
    }
}
