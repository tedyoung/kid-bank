package com.learnwithted.kidbank.domain;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTransactionTest {

  private static final LocalDateTime NOW = LocalDateTime.now();

  @Test
  public void newAccountShouldHaveZeroTransactions() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();

    assertThat(account.transactions())
        .isEmpty();
  }

  @Test
  public void depositToAccountShouldResultInOneCorrectTransaction() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();
    UserProfile userProfile = new DummyUserProfile();

    account.deposit(NOW, 123, "Bottle Return", userProfile);

    Transaction expectedTransaction =
        new Transaction(0L, NOW, Action.DEPOSIT, 123, "Bottle Return", userProfile);

    assertThat(account.transactions())
        .hasSize(1);

    assertThat(account.transactions().iterator().next())
        .isEqualToComparingFieldByField(expectedTransaction);
  }
}
