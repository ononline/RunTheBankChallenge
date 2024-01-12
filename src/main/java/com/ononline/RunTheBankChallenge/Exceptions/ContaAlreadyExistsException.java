package com.ononline.RunTheBankChallenge.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando uma conta já existe na base de dados.
 */
@ResponseStatus(value=HttpStatus.CONFLICT, reason="Conta já existente na base")
public class ContaAlreadyExistsException extends RuntimeException{
}
