    package com.ononline.RunTheBankChallenge.Exceptions;
    
    import org.springframework.http.HttpStatus;
    import org.springframework.web.bind.annotation.ResponseStatus;
    
    /**
     * Exceção lançada quando um cliente não é encontrado na base de dados.
     */
    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Cliente não encontrado na base")
    public class ClienteNotFoundException extends RuntimeException{
        public ClienteNotFoundException(){
            super("Cliente não encontrado");
    }
        
        public ClienteNotFoundException(String message){
            super(message);
        }
    }
