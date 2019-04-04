package com.learnwithted.kidbank.domain;

import org.junit.Test;

import static com.learnwithted.kidbank.domain.TestClockSupport.localDateTimeAtMidnightOf;
import static org.assertj.core.api.Assertions.assertThat;

public class SpendTest {

  @Test
  public void spendMoneyShouldReduceAccountBalance() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();

    account.spend(localDateTimeAtMidnightOf(2012, 10, 11), 5695, "New Switch Game");

    assertThat(account.balance())
        .isEqualTo(-5695);
  }

  @Test
  public void spendMoneyTwiceShouldReduceAccountBalanceBySumOfAllSpending() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();

    account.spend(localDateTimeAtMidnightOf(2013, 12, 11), 1695, "New Game");
    account.spend(localDateTimeAtMidnightOf(2013, 12, 12), 3100, "New Game");

    assertThat(account.balance())
        .isEqualTo(-4795);
  }
}
