package com.learnwithted.kidbank.domain;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class BalanceTest {

  @Test
  public void newAccountShouldHaveZeroBalance() throws Exception {
    Account account = new Account(new FakeTransactionRepository());

    assertThat(account.balance())
        .isZero();
  }

  @Test
  public void mixingDeposit17AndSpend9ShouldResultInBalanceOf8() throws Exception {
    Account account = new Account(new FakeTransactionRepository());

    account.deposit(LocalDateTime.of(2011, 5, 1, 0, 0), 1700, "Bottle Deposit");
    account.spend(LocalDateTime.of(2011, 7, 11, 0, 0), 900, "Cards");

    assertThat(account.balance())
        .isEqualTo(800);
  }

}
