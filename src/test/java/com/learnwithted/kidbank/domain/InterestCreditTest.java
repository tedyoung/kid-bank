package com.learnwithted.kidbank.domain;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

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
  public void interestShouldOnlyBeDepositedOnceForCurrentMonth() throws Exception {
    //    Given today is 2/1/2017
    Clock clock20170201 = createFixedClockOn(2017, 2, 1);

    // Given an account with $100
    Account account = new Account(new FakeTransactionRepository(), clock20170201);
    account.deposit(localDateTimeAtMidnightOf(2016, 6, 7), 10000, "initial deposit");

    // When account balance is requested (will generate interest credit for "today" = 2/1/2017)
    account.balance();

    // Then when we ask for it again, it should still have interested credited only once
    account.balance();

    Set<Transaction> transactions = account.transactions();
    LocalDateTime feb1of2017 = localDateTimeAtMidnightOf(2017, 2, 1);
    long numberFound = transactions.stream()
                                   .filter(Transaction::isInterestCredit)
                                   .filter(t -> t.dateTime().isEqual(feb1of2017))
                                   .count();
    assertThat(numberFound)
        .isEqualTo(1);

  }

  @Test
  public void missingOneMonthCreditShouldAddOneMonthCredit() throws Exception {

    //    Given today is 2/1/2019
    Clock clock20190201 = createFixedClockOn(2019, 2, 1);
    Account account = new Account(new FakeTransactionRepository(), clock20190201);
    account.deposit(localDateTimeAtMidnightOf(2018, 2, 27), 10000, "initial deposit");
    //    And last interest credit was on 1/1/2019
    account.interestCredit(localDateTimeAtMidnightOf(2019, 1, 1), 27);

    //    When we request balance
    account.balance();

    //    Then an interest credit is added for 2/1/2019
    Set<Transaction> transactions = account.transactions();
    LocalDateTime feb1of2019 = localDateTimeAtMidnightOf(2019, 2, 1);
    boolean found = transactions.stream()
                                .filter(Transaction::isInterestCredit)
                                .anyMatch(t -> t.dateTime().isEqual(feb1of2019));
    assertThat(found)
        .isTrue();
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