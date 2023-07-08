package com.wut.money.transfer.app.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;



@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transaction")
public class UserTransactions {


    @Id
    private String transactionId;
    private LocalDateTime dateTime;
    private String transactionType;
    private double accountBalance;
    private double transactionAmount;
    private  String currencyType;


    public UserTransactions(LocalDateTime dateTime, String transactionType, double accountBalance, double transactionAmount, String currencyType) {
        this.dateTime = dateTime;
        this.transactionType = transactionType;
        this.accountBalance = accountBalance;
        this.transactionAmount = transactionAmount;
        this.currencyType = currencyType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }


    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}
