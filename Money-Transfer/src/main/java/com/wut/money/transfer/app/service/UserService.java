package com.wut.money.transfer.app.service;


import com.wut.money.transfer.app.bean.Account;
import com.wut.money.transfer.app.bean.User;
import com.wut.money.transfer.app.bean.UserTransactions;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface UserService {

    public User createNewUser(User user);
    public User findUserById(int  userId);
    public User updateUser(User user, int userId);
    public User deleteUserById(int userId);
    public List<User> finAllUser( );
    public List<User> finUserByLastName(String userName);
    public User userLogin(int UserId);
    public List<Account> getAllAccountByUserId(int userId);
    public List<UserTransactions> getAllUserTransaction(int userId);
    
}
