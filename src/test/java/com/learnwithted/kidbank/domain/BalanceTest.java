package com.learnwithted.kidbank.domain;

import org.junit.Test;

import java.time.LocalDateTime;

import static com.learnwithted.kidbank.domain.TestClockSupport.localDateTimeAtMidnightOf;
import static org.assertj.core.api.Assertions.assertThat;

public class BalanceTest {

  @Test
  public void newAccountShouldHaveZeroBalance() throws Exception {
    Account account = TestAccountBuilder.builder()
                                        .buildAsCore();

    assertThat(account.balance())
        .isZero();
  }

  @Test
  public void mixingDeposit17AndSpend9ShouldResultInBalanceOf8() throws Exception {
    Account account = TestAccountBuilder.builder()
                                        .buildAsCore();

    account.deposit(LocalDateTime.of(2011, 5, 11, 0, 0), 1700, "Bottle Deposit");
    account.spend(LocalDateTime.of(2011, 5, 11, 0, 0), 900, "Cards");

    assertThat(account.balance())
        .isEqualTo(800);
  }

  @Test
  public void balanceUpToEarlierDateDoesNotIncludeMoreRecentTransactions() throws Exception {
    Account account = TestAccountBuilder.builder()
                                        .buildAsCore();

    account.deposit(localDateTimeAtMidnightOf(2011, 5, 11), 17_00, "Bottle Deposit");
    account.spend(localDateTimeAtMidnightOf(2011, 5, 11), 9_00, "Cards");
    account.deposit(localDateTimeAtMidnightOf(2012, 1, 5), 50_00, "Gift");

    assertThat(account.balanceUpTo(localDateTimeAtMidnightOf(2012, 1, 5)))
        .isEqualTo(8_00);
  }

}
