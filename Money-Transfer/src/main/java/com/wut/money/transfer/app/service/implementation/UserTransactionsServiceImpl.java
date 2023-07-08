package com.wut.money.transfer.app.service.implementation;

import com.wut.money.transfer.app.bean.Account;
import com.wut.money.transfer.app.bean.UserTransactions;
import com.wut.money.transfer.app.dto.TransferRequestDTO;
import com.wut.money.transfer.app.exceptions.AccountNotFoundException;
import com.wut.money.transfer.app.exceptions.InsufficientBalanceException;
import com.wut.money.transfer.app.repository.AccountRepository;
import com.wut.money.transfer.app.repository.UserTransactionsRepository;
import com.wut.money.transfer.app.service.AccountService;
import com.wut.money.transfer.app.service.UserTransactionsService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserTransactionsServiceImpl implements UserTransactionsService {

    private final Object lock = new Object();

    @Autowired
    private UserTransactionsRepository transactionsRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    public UserTransactionsRepository getTransactionsRepository() {
        return transactionsRepository;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }


    public UserTransactionsServiceImpl(UserTransactionsRepository transactionsRepository, AccountService accountService, AccountRepository accountRepository) {
        this.transactionsRepository = transactionsRepository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @Override
    public UserTransactions saveTransaction(UserTransactions userTransactions) {
        return this.transactionsRepository.save(userTransactions);
    }

    @Override
    public UserTransactions getTransactionById(String transactionId) {
        return null;
    }


    @Override
    public List<UserTransactions> getAccountTransactionHistory(int accountId) {
        Account account = this.accountService.getAccountById(accountId);
        List<UserTransactions> accountTransactionsList = account.getTransactionsList();
        return  accountTransactionsList;
    }


//    @Override
//    public List<UserTransactions> transferAmountToOtherAccount(TransferRequestDTO transferRequest){
//        List<UserTransactions> userTransactionsList = new ArrayList<UserTransactions>();
//        Account transferAccount = this.accountService.getAccountById(transferRequest.getAccountIdFrom());
//        Account receiverAccount = this.accountService.getAccountById(transferRequest.getAccountIdTO());
//
//        double amountToTransfer = transferRequest.getBalance();
//        double transferAccountBalance = transferAccount.getAccountBalance();
//
//        if(amountToTransfer > transferAccountBalance){
//            throw new InsufficientBalanceException();
//        }else {
//
//
//            Account accountFrom = this.accountService.depositMoney(amountToTransfer, transferAccount.getAccountId());
//            UserTransactions userTransactionsDebit = new UserTransactions();
//            userTrans     actionsDebit.setTransactionId(new ObjectId().toString());  // Set transactionId as string
//            userTransactionsDebit.setTransactionType("Credit");
//            userTransactionsDebit.setCurrencyType(accountFrom.getAccountCurrency());
//            userTransactionsDebit.setAccountBalance(transferAccount.getAccountBalance() + amountToTransfer);
//            userTransactionsDebit.setDateTime(LocalDateTime.now());
//            userTransactionsDebit.setTransactionAmount(amountToTransfer);
//            UserTransactions debitTransaction = this.transactionsRepository.save(userTransactionsDebit);
//
//            accountFrom.addTransaction(debitTransaction);
//            Account updatedFromAccount = this.accountRepository.save(accountFrom);
//            userTransactionsList.add(debitTransaction);
//
//
//
//            Account accountTo = this.accountService.depositMoney(amountToTransfer, receiverAccount.getAccountId());
//            UserTransactions userTransactionsCredit = new UserTransactions();
//            userTransactionsCredit.setTransactionId(new ObjectId().toString());  // Set transactionId as string
//            userTransactionsCredit.setTransactionType("Credit");
//            userTransactionsCredit.setCurrencyType(accountTo.getAccountCurrency());
//            userTransactionsCredit.setAccountBalance(receiverAccount.getAccountBalance() + amountToTransfer);
//            userTransactionsCredit.setDateTime(LocalDateTime.now());
//            userTransactionsCredit.setTransactionAmount(amountToTransfer);
//            UserTransactions creditTransaction = this.transactionsRepository.save(userTransactionsCredit);
//
//            accountTo.addTransaction(creditTransaction);
//            Account updatedToAccount = this.accountRepository.save(accountTo);
//            userTransactionsList.add(creditTransaction);
//
//
//        }
//        return userTransactionsList;
//    }


    @Override
    public synchronized List<UserTransactions> transferAmountToOtherAccount(TransferRequestDTO transferRequest) {
        List<UserTransactions> userTransactionsList = new ArrayList<>();

        Account transferAccount = this.accountService.getAccountById(transferRequest.getAccountIdFrom());
        Account receiverAccount = this.accountService.getAccountById(transferRequest.getAccountIdTO());

        double amountToTransfer = transferRequest.getBalance();
        double transferAccountBalance = transferAccount.getAccountBalance();

        if (amountToTransfer > transferAccountBalance) {
            throw new InsufficientBalanceException();
        } else {
            // A withdrawal is money that's taken out of your account.  -  credits
            Thread creditThread = new Thread(() -> {
                synchronized (transferAccount) {
                    Account accountFrom = this.accountService.withdrawalAccount(amountToTransfer, transferAccount.getAccountId());
                    UserTransactions userTransactionsCredit = new UserTransactions();
                    userTransactionsCredit.setTransactionId(new ObjectId().toString());
                    userTransactionsCredit.setTransactionType("Credit");
                    userTransactionsCredit.setCurrencyType(accountFrom.getAccountCurrency());
                    userTransactionsCredit.setAccountBalance(transferAccount.getAccountBalance() - amountToTransfer);
                    userTransactionsCredit.setDateTime(LocalDateTime.now());
                    userTransactionsCredit.setTransactionAmount(amountToTransfer);
                    UserTransactions creditTransaction = this.transactionsRepository.save(userTransactionsCredit);

                    accountFrom.addTransaction(creditTransaction);
                    Account updatedToAccount = this.accountRepository.save(accountFrom);

                    synchronized (userTransactionsList) {
                        userTransactionsList.add(creditTransaction);
                    }
                }
            });

            // A deposit or Debits is money put into a bank account for safekeeping until you need it.  +
            Thread debitThread = new Thread(() -> {
                synchronized (receiverAccount) {
                    Account accountTo = this.accountService.depositMoney(amountToTransfer, receiverAccount.getAccountId());
                    UserTransactions userTransactionsDebit = new UserTransactions();
                    userTransactionsDebit.setTransactionId(new ObjectId().toString());
                    userTransactionsDebit.setTransactionType("Debit");
                    userTransactionsDebit.setCurrencyType(accountTo.getAccountCurrency());
                    userTransactionsDebit.setAccountBalance(receiverAccount.getAccountBalance() + amountToTransfer);
                    userTransactionsDebit.setDateTime(LocalDateTime.now());
                    userTransactionsDebit.setTransactionAmount(amountToTransfer);
                    UserTransactions debitTransaction = this.transactionsRepository.save(userTransactionsDebit);

                    accountTo.addTransaction(debitTransaction);
                    Account updatedFromAccount = this.accountRepository.save(accountTo);

                    synchronized (userTransactionsList) {
                        userTransactionsList.add(debitTransaction);
                    }
                }
            });

            creditThread.start();
            debitThread.start();

            try {
                creditThread.join();
                debitThread.join();
            } catch (InterruptedException e) {

            }
        }

        return userTransactionsList;
    }






    @Override
    public List<UserTransactions> getAllTransaction(){
        return this.transactionsRepository.findAll();
    }

    @Override
    public List<UserTransactions> getAllDebitedAmount(int accountId){
        Account account = this.accountService.getAccountById(accountId);
        List<UserTransactions> transactions = this.transactionsRepository.findAll();
        List<UserTransactions> debitTransactions = new ArrayList<>();
//        debitTransactions = transactions.stream().map(transaction -> transaction.getTransactionType().toString().equals("Debit")).collect(Collectors.toCollection()))
        for (UserTransactions transaction:transactions) {
            if(transaction.getTransactionType().equals("Debit")){
                debitTransactions.add(transaction);
            }
        }
        return debitTransactions;
    }

    @Override
    public List<UserTransactions> getAllCreditedAmount(int accountId){

        Account account = this.accountService.getAccountById(accountId);
        List<UserTransactions> transactions = this.transactionsRepository.findAll();
        List<UserTransactions> debitTransactions = new ArrayList<>();
//        debitTransactions = transactions.stream().map(transaction -> transaction.getTransactionType() == "Credit").collect(Collectors.toList());
        for (UserTransactions transaction:transactions) {
            if(transaction.getTransactionType().equals("Credit")){
                debitTransactions.add(transaction);
            }
        }
        return debitTransactions;
    }


}
