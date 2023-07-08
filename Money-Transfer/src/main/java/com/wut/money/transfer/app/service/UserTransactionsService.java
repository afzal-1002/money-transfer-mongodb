package com.wut.money.transfer.app.service;

import com.wut.money.transfer.app.bean.UserTransactions;
import com.wut.money.transfer.app.dto.TransferRequestDTO;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface UserTransactionsService {

    public UserTransactions saveTransaction(UserTransactions userTransactions);
    public UserTransactions getTransactionById(String transactionId);

    public List<UserTransactions> getAllTransaction();
    public List<UserTransactions> getAccountTransactionHistory(int accountId);
    public List<UserTransactions> transferAmountToOtherAccount(TransferRequestDTO transferRequest);

    public List<UserTransactions> getAllDebitedAmount(int accountId);
    public List<UserTransactions> getAllCreditedAmount(int accountId);


}
