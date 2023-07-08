package com.wut.money.transfer.app.repository;

import com.wut.money.transfer.app.bean.UserTransactions;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserTransactionsRepository extends MongoRepository<UserTransactions, Integer> {
}
