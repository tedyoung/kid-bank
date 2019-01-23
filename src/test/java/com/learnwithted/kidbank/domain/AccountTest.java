package com.learnwithted.kidbank.domain;

import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {

  @Test
  public void loadTransactionsShouldHaveAccountKnowAboutThoseTransactions() throws Exception {
    Account account = new Account();
    // account.load(Set<Transaction>)

    Set<Transaction> transactionsToLoad = null;

    assertThat(account.transactions())
        .isEqualTo(transactionsToLoad);
  }
}