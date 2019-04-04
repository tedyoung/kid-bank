package com.learnwithted.kidbank.domain;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTransactionTest {

  @Test
  public void newAccountShouldHaveZeroTransactions() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();

    assertThat(account.transactions())
        .isEmpty();
  }

  @Test
  public void depositToAccountShouldResultInOneTransaction() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();

    LocalDateTime transactionDateTime = LocalDateTime.now();

    account.deposit(transactionDateTime, 123, "Bottle Return");

    Transaction expectedTransaction = new Transaction(
        transactionDateTime, "Deposit", 123, "Bottle Return");
    expectedTransaction.setId(0L);

    assertThat(account.transactions())
        .containsOnly(
            expectedTransaction);
  }
}
