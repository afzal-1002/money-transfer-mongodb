package com.wut.money.transfer.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountNotExistsException extends RuntimeException {
    public AccountNotExistsException(String account_not_found) {
        super("Account not found");
    }
}
