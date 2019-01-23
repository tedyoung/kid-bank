package com.learnwithted.kidbank.domain;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class SpendTest {

  @Test
  public void spendMoneyShouldReduceAccountBalance() throws Exception {
    Account account = new Account();

    account.spend(LocalDateTime.of(2012, 10, 11, 0, 0), 5695, "New Switch Game");

    assertThat(account.balance())
        .isEqualTo(-5695);
  }

  @Test
  public void spendMoneyTwiceShouldReduceAccountBalanceBySumOfAllSpending() throws Exception {
    Account account = new Account();

    account.spend(LocalDateTime.of(2013, 12, 11, 0, 0), 1695, "New Game");
    account.spend(LocalDateTime.of(2013, 12, 12, 0, 0), 3100, "New Game");

    assertThat(account.balance())
        .isEqualTo(-4795);
  }
}
