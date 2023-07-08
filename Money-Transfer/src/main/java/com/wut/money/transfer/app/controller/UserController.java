package com.wut.money.transfer.app.controller;

import com.wut.money.transfer.app.bean.Account;
import com.wut.money.transfer.app.bean.User;
import com.wut.money.transfer.app.bean.UserTransactions;
import com.wut.money.transfer.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("")
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/saveUser")
    public ResponseEntity<User> createNewUser(@RequestBody User user){
        User savedUser = null;
        try {
            savedUser = this.userService.createNewUser(user);
            return ResponseEntity.of(Optional.of(savedUser));
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/findUser/{userId}")
    public ResponseEntity<User> findUserById(@PathVariable("userId") int  userId){
        User user = null;
        try{
            user = this.userService.findUserById(userId);
            return ResponseEntity.of(Optional.of(user));
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("userId") int userId){

        try {
            User user1 = this.userService.updateUser(user,userId);
            return ResponseEntity.of(Optional.of(user1));
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<User> deleteUserById(@PathVariable int userId){

        User user = null;
        try {
            user = this.userService.deleteUserById(userId);
            return ResponseEntity.of(Optional.of(user));
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> finAllUser( ){
        List<User> userList = this.userService.finAllUser();
        try {
            return ResponseEntity.of(Optional.of(userList));
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getUserByName/{userName}")
    public ResponseEntity<List<User>> finUserByLastName(@PathVariable String userName){
        List<User> users = this.userService.finUserByLastName(userName);
        try {
            return ResponseEntity.of(Optional.of(users));
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


    @GetMapping("/getAllUserAccount/{userId}")
    public ResponseEntity<List<Account>> getAllAccountByUserId(@PathVariable int userId){
        List<Account> accountList = this.userService.getAllAccountByUserId(userId);

        return ResponseEntity.of(Optional.of(accountList));
    }

    @GetMapping("/getAllUserTransactions/{userId}")
    public ResponseEntity<List<UserTransactions>> getAllUserTransaction(@PathVariable int userId){
        List<UserTransactions> userTransactions = this.userService.getAllUserTransaction(userId);
        return ResponseEntity.of(Optional.of(userTransactions));
    }




}
