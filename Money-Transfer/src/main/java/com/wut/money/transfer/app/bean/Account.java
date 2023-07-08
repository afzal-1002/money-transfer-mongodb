package com.wut.money.transfer.app.bean;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Account {

    @Id
    private int accountId;
    private double accountBalance;
    private String accountCurrency ;
    private User user;

    private List<UserTransactions> transactionsList = new ArrayList<>();




    public void addTransaction(UserTransactions userTransactions){
        this.transactionsList.add(userTransactions);
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency;
    }

    public List<UserTransactions> getTransactionsList() {
        return transactionsList;
    }

    public void setTransactionsList(List<UserTransactions> transactionsList) {
        this.transactionsList = transactionsList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
