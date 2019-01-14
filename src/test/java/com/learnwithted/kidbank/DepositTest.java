package com.learnwithted.kidbank;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DepositTest {

  @Test
  public void deposit495ShouldResultInBalanceOf495() throws Exception {
    Account account = new Account();

    account.deposit(495);

    assertThat(account.balance())
        .isEqualTo(495);
  }

  @Test
  public void deposit100Then200ShouldResultIn300Balance() throws Exception {
    Account account = new Account();

    account.deposit(100);
    account.deposit(200);

    assertThat(account.balance())
        .isEqualTo(300);
  }
}
