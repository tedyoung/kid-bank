package com.learnwithted.kidbank.domain;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

public class InterestCreditTest {

  @Test
  public void onFirstOfMonthInterestShouldBeDepositedIntoAccount() throws Exception {
    Clock clock20170201 = createFixedClockOn(2017, 2, 1);

    // Given an account with $100
    Account account = new Account(new FakeTransactionRepository(), clock20170201);
    account.deposit(localDateTimeAtMidnightOf(2016, 6, 7), 10000, "initial deposit");

    // Then $100 * (2.5% / 12) should be credited into the account
    assertThat(account.balance())
        .isEqualTo(10000 + 21); // rounding up 20.8333 to 21 cents
  }

  @Test
  public void onFirstOfMonthInterestShouldOnlyBeDepositedOnce() throws Exception {
    Clock clock20170201 = createFixedClockOn(2017, 2, 1);

    // Given an account with $100
    Account account = new Account(new FakeTransactionRepository(), clock20170201);
    account.deposit(localDateTimeAtMidnightOf(2016, 6, 7), 10000, "initial deposit");

    // When account balance is requested
    account.balance();

    // Then when we ask for it again, it should still have interested credited only once
    assertThat(account.balance())
        .isEqualTo(10000 + 21); // rounding up 20.8333 to 21 cents

    assertThat(account.transactions())
        .hasSize(2);
  }

  @Test
  public void onNotFirstOfMonthNoInterestShouldBeDepositedIntoAccount() throws Exception {
    Clock clock20180228 = createFixedClockOn(2018, 2, 28);

    Account account = new Account(new FakeTransactionRepository(), clock20180228);
    account.deposit(localDateTimeAtMidnightOf(2018, 2, 27), 10000, "initial deposit");

    assertThat(account.balance())
        .isEqualTo(10000);
  }

  public Clock createFixedClockOn(int year, int month, int day) {
    LocalDateTime dateTime = localDateTimeAtMidnightOf(year, month, day);
    Instant instant = Instant.from(dateTime.atZone(ZoneId.systemDefault()));
    return Clock.fixed(instant, ZoneId.systemDefault());
  }

  public LocalDateTime localDateTimeAtMidnightOf(int year, int month, int day) {
    return LocalDateTime.of(year, month, day, 0, 0);
  }
}