package com.ononline.RunTheBankChallenge.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando um cliente já existe na base de dados.
 */
@ResponseStatus(value=HttpStatus.CONFLICT, reason="Cliente já existente na base")
public class ClienteAlreadyExistsException extends RuntimeException{
}
