package com.wut.money.transfer.app;

import com.wut.money.transfer.app.bean.Account;
import com.wut.money.transfer.app.repository.UserRepository;
import com.wut.money.transfer.app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MoneyTransferApplication {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AccountService accountService;

	public static void main(String[] args) {

		SpringApplication.run(MoneyTransferApplication.class, args);
	}


//    public void run(String... args) throws Exception {
//
//		List<Account> accountList = this.accountService.getAllAccount();
//		for (Account account:accountList) {
//			System.out.println(account.getUser().getCustomerId());
//		}
//	}
}
