package com.learnwithted.kidbank.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BalanceTest {

  @Test
  public void newAccountShouldHaveZeroBalance() throws Exception {
    Account account = new Account();

    assertThat(account.balance())
        .isZero();
  }

}
