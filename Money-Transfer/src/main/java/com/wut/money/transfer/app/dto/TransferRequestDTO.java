package com.wut.money.transfer.app.dto;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TransferRequestDTO {
    private double balance;
    private int accountIdFrom;
    private int accountIdTO;


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getAccountIdFrom() {
        return accountIdFrom;
    }

    public void setAccountIdFrom(int accountIdFrom) {
        this.accountIdFrom = accountIdFrom;
    }

    public int getAccountIdTO() {
        return accountIdTO;
    }

    public void setAccountIdTO(int accountIdTO) {
        this.accountIdTO = accountIdTO;
    }
}
