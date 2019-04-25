package com.learnwithted.kidbank.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.learnwithted.kidbank.domain.TestClockSupport.localDateTimeAtMidnightOf;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {

  @Test
  public void loadTransactionsIntoCoreAccountStoresThoseTransactions() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();

    List<Transaction> transactionsToLoad = new ArrayList<>();
    transactionsToLoad.add(
        Transaction.createDeposit(localDateTimeAtMidnightOf(2015, 5, 8), 7825, "txn 1"));
    transactionsToLoad.add(
        Transaction.createSpend(localDateTimeAtMidnightOf(2015, 5, 9), 2595, "txn 2"));

    account.load(transactionsToLoad);

    assertThat(account.transactions())
        .containsExactlyInAnyOrderElementsOf(transactionsToLoad);
  }

//  @Test
//  public void interestEarningAccountIgnoresInterestCreditsViaLoad() throws Exception {
//    TestAccountBuilder builder = TestAccountBuilder.builder();
//    Account interestEarningAccount = builder.buildAsInterestEarning(2015, 6, 2);
//    CoreAccount coreAccount = builder.coreAccount();
//
//    List<Transaction> transactionsToLoad = new ArrayList<>();
//    transactionsToLoad.add(
//        Transaction.createDeposit(localDateTimeAtMidnightOf(2015, 5, 8), 78_25, "deposit"));
//    transactionsToLoad.add(
//        Transaction.createSpend(localDateTimeAtMidnightOf(2015, 5, 9), 25_95, "spend"));
//    transactionsToLoad.add(
//        Transaction.createInterestCredit(localDateTimeAtMidnightOf(2015, 6, 1), 2_15));
//
//    interestEarningAccount.load(transactionsToLoad);
//
//    assertThat(coreAccount.transactions())
//        .hasSize(2);
//  }

}