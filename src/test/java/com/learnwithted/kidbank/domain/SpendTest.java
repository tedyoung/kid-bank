package com.learnwithted.kidbank.domain;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;
import org.junit.Test;

import java.time.Clock;

import static com.learnwithted.kidbank.domain.TestClockSupport.createFixedClockOn;
import static com.learnwithted.kidbank.domain.TestClockSupport.localDateTimeAtMidnightOf;
import static org.assertj.core.api.Assertions.assertThat;

public class SpendTest {

  @Test
  public void spendMoneyShouldReduceAccountBalance() throws Exception {
    Clock clock = createFixedClockOn(2012, 10, 11);
    Account account = new Account(new FakeTransactionRepository(), clock);

    account.spend(localDateTimeAtMidnightOf(2012, 10, 11), 5695, "New Switch Game");

    assertThat(account.balance())
        .isEqualTo(-5695);
  }

  @Test
  public void spendMoneyTwiceShouldReduceAccountBalanceBySumOfAllSpending() throws Exception {
    Clock clock = createFixedClockOn(2013, 12, 12);
    Account account = new Account(new FakeTransactionRepository(), clock);

    account.spend(localDateTimeAtMidnightOf(2013, 12, 11), 1695, "New Game");
    account.spend(localDateTimeAtMidnightOf(2013, 12, 12), 3100, "New Game");

    assertThat(account.balance())
        .isEqualTo(-4795);
  }
}
