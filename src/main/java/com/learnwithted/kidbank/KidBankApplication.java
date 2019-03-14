package com.learnwithted.kidbank;

import com.learnwithted.kidbank.adapter.sms.TwilioSmsBalanceChangedNotifier;
import com.learnwithted.kidbank.config.TwilioConfig;
import com.learnwithted.kidbank.domain.BalanceChangedNotifier;
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
  public BalanceChangedNotifier balanceChangedNotifier(TwilioConfig twilioConfig,
        @Value("${feature.notify.balance.change}") boolean isEnabled) {
    log.info("Notify Balance Change is " + isEnabled);
    if (isEnabled) {
      return new TwilioSmsBalanceChangedNotifier(twilioConfig);
    } else {
      return (amount, balance) -> {};
    }
  }

}

