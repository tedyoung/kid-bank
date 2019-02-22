package com.learnwithted.kidbank.domain;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;

import static com.learnwithted.kidbank.domain.TestClockSupport.createFixedClockOn;
import static org.assertj.core.api.Assertions.assertThat;

public class BalanceTest {

  @Test
  public void newAccountShouldHaveZeroBalance() throws Exception {
    Account account = new Account(new FakeTransactionRepository(), new StubBalanceChangeNotifier());

    assertThat(account.balance())
        .isZero();
  }

  @Test
  public void mixingDeposit17AndSpend9ShouldResultInBalanceOf8() throws Exception {
    Clock clock = createFixedClockOn(2011, 5, 11);
    Account account = new Account(new FakeTransactionRepository(), clock);

    account.deposit(LocalDateTime.of(2011, 5, 11, 0, 0), 1700, "Bottle Deposit");
    account.spend(LocalDateTime.of(2011, 5, 11, 0, 0), 900, "Cards");

    assertThat(account.balance())
        .isEqualTo(800);
  }

}
