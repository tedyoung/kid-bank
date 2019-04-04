package com.learnwithted.kidbank.domain;

import org.junit.Test;

import static com.learnwithted.kidbank.domain.TestClockSupport.localDateTimeAtMidnightOf;
import static org.assertj.core.api.Assertions.assertThat;

public class DepositTest {

  @Test
  public void deposit495ShouldResultInBalanceOf495() throws Exception {
    Account account = TestAccountBuilder.builder()
                                        .buildAsInterestEarning();

    account.deposit(localDateTimeAtMidnightOf(2018, 12, 27), 495, "Bottle Return");

    assertThat(account.balance())
        .isEqualTo(495);
  }

  @Test
  public void deposit100Then200ShouldResultIn300Balance() throws Exception {
    Account account = TestAccountBuilder.builder()
                                        .buildAsInterestEarning();

    account.deposit(localDateTimeAtMidnightOf(2019, 2, 13), 100, "Bottle Return");
    account.deposit(localDateTimeAtMidnightOf(2019, 2, 13), 200, "Bottle Return");

    assertThat(account.balance())
        .isEqualTo(300);
  }
}
