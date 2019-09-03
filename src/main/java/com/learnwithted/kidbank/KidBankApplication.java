package com.learnwithted.kidbank;

import com.learnwithted.kidbank.adapter.sms.TextMessageBalanceChangedNotifier;
import com.learnwithted.kidbank.app.TextMessageSender;
import com.learnwithted.kidbank.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
@Slf4j
public class KidBankApplication {

  public static void main(String[] args) {
    SpringApplication.run(KidBankApplication.class, args);
  }

  @Bean
  public Clock clock() {
    return Clock.systemDefaultZone();
  }

  @Bean
  public Account account(TransactionRepository transactionRepository,
      GoalRepository goalRepository,
      BalanceChangedNotifier balanceChangedNotifier,
      InterestStrategy interestStrategy) {
    CoreAccount coreAccount = new CoreAccount(transactionRepository, goalRepository, balanceChangedNotifier);
    return new InterestEarningAccount(coreAccount, interestStrategy);
  }

  @Bean
  public BalanceChangedNotifier balanceChangedNotifier(TextMessageSender textMessageSender,
      @Value("${feature.notify.balance.change}") boolean isEnabled) {
    log.info("Notify Balance Change is " + isEnabled);
    if (isEnabled) {
      return new TextMessageBalanceChangedNotifier(textMessageSender);
    } else {
      return new DoNothingBalanceChangeNotifier();
    }
  }

}

