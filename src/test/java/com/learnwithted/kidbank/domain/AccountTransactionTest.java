package com.learnwithted.kidbank.domain;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTransactionTest {

  @Test
  public void newAccountShouldHaveZeroTransactions() throws Exception {
    Account account = new Account(new FakeTransactionRepository());

    assertThat(account.transactions())
        .isEmpty();
  }

  @Test
  public void depositToAccountShouldResultInOneTransaction() throws Exception {
    Account account = new Account(new FakeTransactionRepository());

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
