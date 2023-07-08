package com.wut.money.transfer.app.repository;

import com.wut.money.transfer.app.bean.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, Integer> {


}
