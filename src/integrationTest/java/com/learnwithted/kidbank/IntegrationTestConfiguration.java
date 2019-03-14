package com.learnwithted.kidbank;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;
import com.learnwithted.kidbank.domain.InterestStrategy;
import com.learnwithted.kidbank.domain.TransactionRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.test.context.TestPropertySource;

@TestConfiguration
@TestPropertySource(properties = "feature.notify.balance.change=false")
@EnableResourceServer
public class IntegrationTestConfiguration extends ResourceServerConfigurerAdapter {

  /**
   * This enables the @WithMockUser to work with the OAuth2 security
   *
   * From answer here: https://stackoverflow.com/a/44502136/75113
   */
  @Override
  public void configure(ResourceServerSecurityConfigurer security) throws Exception {
    security.stateless(false);
  }

  @Bean
  public TransactionRepository transactionRepository() {
    return new FakeTransactionRepository();
  }

  @Bean
  public InterestStrategy interestStrategy() {
    return account -> {
    };
  }

}

