package com.wut.money.transfer.app.service.implementation;


import com.wut.money.transfer.app.bean.Account;
import com.wut.money.transfer.app.bean.User;
import com.wut.money.transfer.app.bean.UserTransactions;
import com.wut.money.transfer.app.repository.UserRepository;
import com.wut.money.transfer.app.service.AccountService;
import com.wut.money.transfer.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountService accountService;

    @Override
    public User createNewUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User findUserById(int userId) {
        Optional<User> optionalUser = this.userRepository.findById(userId);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }else {
            return null;
        }
    }

    @Override
    public User updateUser(User user, int userId) {
        Optional<User> optionalCustomer = this.userRepository.findById(userId);
        if(optionalCustomer.isPresent()){
            User updateUser = optionalCustomer.get();
            updateUser.setFirstName(user.getFirstName());
            updateUser.setLastName(user.getLastName());
            updateUser.setEmail(user.getEmail());
            updateUser.setPassword(user.getPassword());
            this.userRepository.save(updateUser);
            return updateUser;

        }else {
            return null;
        }
    }

    @Override
    public User deleteUserById(int userId) {
        Optional<User> user = this.userRepository.findById(userId);
        if(user.isPresent()){
            User deleteUser = user.get();
            this.userRepository.delete(deleteUser);
            return deleteUser;
        }else {
            return null;
        }
    }

    @Override
    public List<User> finAllUser() {
        return this.userRepository.findAll();
    }

    @Override
    public List<User> finUserByLastName(String userName) {
        return this.userRepository.findAllByLastName(userName);
    }

    @Override
    public User userLogin(int UserId) {
        Optional<User> optionalCustomer = this.userRepository.findById(UserId);
        User user = this.findUserById(UserId);
        if(optionalCustomer.isPresent()){
            return optionalCustomer.get();
        }else {
            return null;
        }

    }

    @Override
    public List<Account> getAllAccountByUserId(int userId) {
        List<Account> accountList = this.accountService.getAllAccount();
        List<Account> userAccount = new ArrayList<Account>();
        for (Account account:accountList) {
          if (account.getUser().getCustomerId() == userId){
              userAccount.add(account);
          }

        }

        return userAccount;
    }

    @Override
    public List<UserTransactions> getAllUserTransaction(int userId) {

        List<Account> accountList = this.getAllAccountByUserId(userId);

        List<UserTransactions> userTransactionsList = new ArrayList<>();

        for (Account account: accountList) {
            List<UserTransactions> userTransactions = account.getTransactionsList();
            for (UserTransactions transaction: userTransactions) {
                userTransactionsList.add(transaction);
            }
        }

        return userTransactionsList;
    }
}
