package com.wut.money.transfer.app.service;

import com.wut.money.transfer.app.bean.Account;
import com.wut.money.transfer.app.bean.User;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface AccountService {

    public Account createNewAccount(Account account,int userId);

    public Account getAccountById(int accountId);

    public List<Account> getAllAccount();

    public List<Account> getAccountStatement(int accountId);

    public double getAccountBalance(int accountId);

    public Account updateBankAccount(Account account);

    public Account setUserAccount(Account account, int accountId);

    public Account deleteAccountById(int accountId);

    public Account depositMoney(double amount, int accountId);

    public Account withdrawalAccount(double amount, int accountId);


}
