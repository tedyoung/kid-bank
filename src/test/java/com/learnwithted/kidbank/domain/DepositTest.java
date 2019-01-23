package com.learnwithted.kidbank.domain;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class DepositTest {

  @Test
  public void deposit495ShouldResultInBalanceOf495() throws Exception {
    Account account = new Account();

    account.deposit(LocalDateTime.now(), 495, "Bottle Return");

    assertThat(account.balance())
        .isEqualTo(495);
  }

  @Test
  public void deposit100Then200ShouldResultIn300Balance() throws Exception {
    Account account = new Account();

    account.deposit(LocalDateTime.now(), 100, "Bottle Return");
    account.deposit(LocalDateTime.now(), 200, "Bottle Return");

    assertThat(account.balance())
        .isEqualTo(300);
  }
}
