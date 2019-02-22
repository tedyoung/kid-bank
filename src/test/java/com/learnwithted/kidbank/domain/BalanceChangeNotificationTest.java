package com.learnwithted.kidbank.domain;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;
import org.junit.Test;

import static java.time.LocalDateTime.now;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BalanceChangeNotificationTest {
  @Test
  public void depositShouldCauseNotificationOfBalanceChange() throws Exception {
    BalanceChangedNotifier mockNotifier = mock(BalanceChangedNotifier.class);
    // Given an account
    Account account = new Account(new FakeTransactionRepository(), mockNotifier);

    // When we deposit money
    account.deposit(now(), 25_00, "test");

    // Then we expect the notification to be sent
    verify(mockNotifier).balanceChanged(25_00, 25_00);
  }

  @Test
  public void spendShouldCauseNotificationOfBalanceChange() throws Exception {
    BalanceChangedNotifier mockNotifier = mock(BalanceChangedNotifier.class);
    // Given an account with $100
    FakeTransactionRepository transactionRepository = new FakeTransactionRepository();
    transactionRepository.save(Transaction.createDeposit(now(), 100_00, "initialize"));

    Account account = new Account(transactionRepository, mockNotifier);

    account.spend(now(), 35_35, "test");

    verify(mockNotifier).balanceChanged(-35_35, 64_65);
  }
}
