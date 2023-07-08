package com.wut.money.transfer.app.repository;

import com.wut.money.transfer.app.bean.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, Integer> {
    public List<User> findAllByLastName(String lastName);
}
