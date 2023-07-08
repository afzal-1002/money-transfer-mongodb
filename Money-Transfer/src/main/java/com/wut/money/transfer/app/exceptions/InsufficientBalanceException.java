package com.wut.money.transfer.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException (){
        super("Insufficient Amount");
    }

    public InsufficientBalanceException(String s) {
    }
}
