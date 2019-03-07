package com.learnwithted.kidbank;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;
import com.learnwithted.kidbank.domain.InterestStrategy;
import com.learnwithted.kidbank.domain.TransactionRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;

@TestConfiguration
@TestPropertySource(properties = "feature.notify.balance.change=false")
public class IntegrationTestConfiguration {

  @Bean
  public TransactionRepository transactionRepository() {
    return new FakeTransactionRepository();
  }

  @Bean
  public InterestStrategy interestStrategy() {
    return account -> { };
  }

}

