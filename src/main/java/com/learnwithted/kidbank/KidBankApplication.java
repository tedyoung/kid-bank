package com.learnwithted.kidbank;

import com.learnwithted.kidbank.domain.Account;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KidBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(KidBankApplication.class, args);
	}

	@Bean
  public Account singletonAccount() {
    return new Account();
  }

}

