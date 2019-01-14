package com.learnwithted.kidbank;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTransactionTest {

  @Test
  public void newAccountShouldHaveZeroTransactions() throws Exception {
    Account account = new Account();

    assertThat(account.transactions())
        .isEmpty();
  }

  @Test
  public void depositToAccountShouldResultInOneTransaction() throws Exception {
    Account account = new Account();

    account.deposit(123);

    assertThat(account.transactions())
        .hasSize(1);
  }
}
