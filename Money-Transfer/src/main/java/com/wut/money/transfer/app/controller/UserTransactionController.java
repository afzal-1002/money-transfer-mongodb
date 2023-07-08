package com.wut.money.transfer.app.controller;

import com.wut.money.transfer.app.bean.Account;
import com.wut.money.transfer.app.bean.UserTransactions;
import com.wut.money.transfer.app.dto.TransferRequestDTO;
import com.wut.money.transfer.app.exceptions.AccountNotFoundException;
import com.wut.money.transfer.app.exceptions.InsufficientBalanceException;
import com.wut.money.transfer.app.repository.AccountRepository;
import com.wut.money.transfer.app.service.AccountService;
import com.wut.money.transfer.app.service.UserTransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@RestController("")
public class UserTransactionController {


    @Autowired
    private UserTransactionsService transactionsService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;


    @PostMapping("/transferFund")
    public ResponseEntity<String> transferAmountToOtherAccount(@RequestBody TransferRequestDTO request) {
        try {
            List<UserTransactions> transfer = this.transactionsService.transferAmountToOtherAccount(request);
            return ResponseEntity.ok("Transfer Completed");
        } catch (InsufficientBalanceException e) {
            String errorMessage = "Insufficient balance in the account.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } catch (AccountNotFoundException ex){
            Account transferAccount = this.accountService.getAccountById(request.getAccountIdFrom());
            Account receiverAccount = this.accountService.getAccountById(request.getAccountIdTO());
            String accountNotFound = "";

            if(transferAccount == null){
               accountNotFound =   "User Account " + request.getAccountIdFrom() + " Not Found";
            }else if (receiverAccount ==  null){
                 accountNotFound =   "User Account " + request.getAccountIdTO() + " Not Found";
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(accountNotFound);
        }

    }


    @GetMapping("/getAllTransactions")
    public ResponseEntity<List<UserTransactions>> getAllTransaction(){
        List<UserTransactions>  transactionsList  = this.transactionsService.getAllTransaction();
        return ResponseEntity.of(Optional.of(transactionsList));
    }


    @GetMapping("/getTransactionById/{transactionId}")
    public ResponseEntity<UserTransactions> getTransactionById(@PathVariable String transactionId){
        UserTransactions userTransactions = this.transactionsService.getTransactionById(transactionId);
        return ResponseEntity.of(Optional.of(userTransactions));
    }


    @GetMapping("/transactionHistory/{accountId}")
    public ResponseEntity<List<UserTransactions>> getAccountTransactionHistory(@PathVariable int accountId) {
        List<UserTransactions> transactions = this.transactionsService.getAccountTransactionHistory(accountId);
        return ResponseEntity.of(Optional.of(transactions));
    }

    @GetMapping("/allDebitedAmount/{accountId}")
    public ResponseEntity<List<UserTransactions>> getAllDebitedAmount(@PathVariable int accountId){
        List<UserTransactions> transactions = this.transactionsService.getAllDebitedAmount(accountId);
        return ResponseEntity.of(Optional.of(transactions));
    }
    @GetMapping("/allCreditedAmount/{accountId}")
    public ResponseEntity<List<UserTransactions>> getAllCreditedAmount(@PathVariable int accountId){
        List<UserTransactions> transactions = this.transactionsService.getAllCreditedAmount(accountId);
        return ResponseEntity.of(Optional.of(transactions));
    }





}
