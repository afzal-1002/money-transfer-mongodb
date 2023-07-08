package com.wut.money.transfer.app.service.implementation;

import com.wut.money.transfer.app.bean.Account;
import com.wut.money.transfer.app.bean.User;
import com.wut.money.transfer.app.bean.UserTransactions;
import com.wut.money.transfer.app.exceptions.AccountNotFoundException;
import com.wut.money.transfer.app.exceptions.InsufficientBalanceException;
import com.wut.money.transfer.app.repository.AccountRepository;
import com.wut.money.transfer.app.repository.UserRepository;
import com.wut.money.transfer.app.service.AccountService;
import com.wut.money.transfer.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class AccountServiceImplementation implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private  UserRepository userRepository;


    @Override
    public Account createNewAccount(Account account,int userId){
        Account savedAccount = this.accountRepository.save(account);
        Account updatedAccount = this.setUserAccount(account, userId);

        return updatedAccount;

    }
    @Override
    public Account getAccountById(int accountId){
        Optional<Account> account = this.accountRepository.findById(accountId);
        if(account.isPresent()){
            return account.get();
        }else {
            return  null;
        }
    }

    @Override
    public List<Account> getAllAccount() {
        return this.accountRepository.findAll();
    }

    @Override
    public List<Account> getAccountStatement(int accountId) {
        return  this.accountRepository.findAllById(Collections.singleton(accountId));
    }


    @Override
    public double getAccountBalance(int accountId) {
        Optional<Account> optionalAccount = this.accountRepository.findById(accountId);
        if(optionalAccount.isPresent()){
            Account account = optionalAccount.get();
            double amount;
            amount = account.getAccountBalance();
            return amount;
        }else{
            return 0.0;
        }
    }

    @Override
    public Account updateBankAccount(Account account) {
        Optional<Account> optionalAccount = this.accountRepository.findById(account.getAccountId());
        if(optionalAccount.isPresent()){
            Account updateAccount = optionalAccount.get();

            updateAccount.setAccountBalance(account.getAccountBalance());
            updateAccount.setAccountCurrency(account.getAccountCurrency());

            List<UserTransactions> transactions = account.getTransactionsList();
            if (transactions.isEmpty()){
                updateAccount.setTransactionsList(account.getTransactionsList());
            }else {
                updateAccount.setTransactionsList(transactions);
            }
            updateAccount.setUser(account.getUser());
            this.accountRepository.save(updateAccount);
            return updateAccount;
        }else {
            return null;
        }

    }

    @Override
    public Account setUserAccount(Account account, int userId) {
        Optional<Account> optionalAccount = this.accountRepository.findById(account.getAccountId());
        if(optionalAccount.isPresent()){
            Account updateAccount = optionalAccount.get();

            updateAccount.setAccountBalance(account.getAccountBalance());
            updateAccount.setAccountCurrency(account.getAccountCurrency());

            List<UserTransactions> transactions = account.getTransactionsList();

            if (transactions.isEmpty()){
                updateAccount.setTransactionsList(account.getTransactionsList());
            }else {
                updateAccount.setTransactionsList(transactions);
            }

            Optional<User> user = this.userRepository.findById(userId);


            if(user.isPresent()){
                updateAccount.setUser(user.get());
            }

            this.accountRepository.save(updateAccount);
            return updateAccount;


        }else {
            return null;
        }

    }

    @Override
    public Account deleteAccountById(int accountId) {
        Optional<Account> account = this.accountRepository.findById(accountId);
        if(account.isPresent()){
            this.accountRepository.deleteById(accountId);
            return account.get();
        }else {
            return null;
        }
    }


    // Add Money to account
    // A deposit or Debits is money put into a bank account for safekeeping until you need it.  +
    public Account depositMoney(double amount, int accountId){
        Optional<Account> optionalAccount = this.accountRepository.findById(accountId);
        if(optionalAccount.isPresent()){
            Account account = optionalAccount.get();
            double accountBalance = account.getAccountBalance();
            accountBalance = account.getAccountBalance() + amount;

            account.setAccountBalance(accountBalance);
            this.accountRepository.save(account);

            return account;
        }else {
            return null;
        }

    }


    // credit amount from bank account
    // A withdrawal is money that's taken out of your account.  -  credits
    @Override
    public Account withdrawalAccount(double amount, int accountId) {
        Optional<Account> optionalAccount = this.accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            double accountBalance = account.getAccountBalance();

            if (amount > accountBalance) {
                throw new InsufficientBalanceException("Insufficient balance in the account.");
            }

            accountBalance = account.getAccountBalance() - amount;
            account.setAccountBalance(accountBalance);

            return this.accountRepository.save(account);
        } else {
            throw new AccountNotFoundException("Account not found.");
        }
    }




}
