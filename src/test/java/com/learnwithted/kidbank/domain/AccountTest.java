package com.learnwithted.kidbank.domain;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {

  @Test
  public void loadTransactionsShouldHaveAccountKnowAboutThoseTransactions() throws Exception {
    Account account = new Account(new FakeTransactionRepository());

    List<Transaction> transactionsToLoad = new ArrayList<>();
    transactionsToLoad.add(
        Transaction.createDeposit(LocalDateTime.of(2015, 5, 8, 0, 0), 7825, "txn 1"));
    transactionsToLoad.add(
        Transaction.createSpend(LocalDateTime.of(2015, 5, 9, 0, 0), 2595, "txn 2"));

    account.load(transactionsToLoad);

    assertThat(account.transactions())
        .containsExactlyInAnyOrderElementsOf(transactionsToLoad);
  }

}