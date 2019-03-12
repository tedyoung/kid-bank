package com.learnwithted.kidbank.domain;

import org.junit.Test;

import static java.time.LocalDateTime.now;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BalanceChangeNotificationTest {
  @Test
  public void depositShouldCauseNotificationOfBalanceChange() throws Exception {
    BalanceChangedNotifier mockNotifier = mock(BalanceChangedNotifier.class);

    // Given an account
    Account account = TestAccountBuilder.builder()
                                        .notifier(mockNotifier)
                                        .build();

    // When we deposit money
    account.deposit(now(), 25_00, "test");

    // Then we expect the notification to be sent
    verify(mockNotifier).balanceChanged(25_00, 25_00);
  }

  @Test
  public void spendShouldCauseNotificationOfBalanceChange() throws Exception {
    BalanceChangedNotifier mockNotifier = mock(BalanceChangedNotifier.class);

    Account account = TestAccountBuilder.builder()
                                        .initialBalanceOf(100_00)
                                        .notifier(mockNotifier)
                                        .build();

    account.spend(now(), 35_35, "test");

    verify(mockNotifier).balanceChanged(-35_35, 64_65);
  }
}
