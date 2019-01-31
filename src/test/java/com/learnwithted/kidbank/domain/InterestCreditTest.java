package com.learnwithted.kidbank.domain;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;

public class InterestCreditTest {

  @Test
  public void onFirstOfMonthInterestShouldBeDepositedIntoAccount() throws Exception {
    Clock clock20170201 = TestClockSupport.createFixedClockOn(2017, 2, 1);

    // Given an account with $100
    Account account = new Account(new FakeTransactionRepository(), clock20170201);
    account.deposit(TestClockSupport.localDateTimeAtMidnightOf(2016, 12, 7), 10000, "initial deposit");

    // Then $100 * (2.5% / 12) should be credited into the account, twice: for 1/1/17 & 2/1/17
    assertThat(account.balance())
        .isEqualTo(10000 + 21 + 21); // rounding up 20.8333 to 21 cents
  }

  @Test
  public void interestShouldOnlyBeDepositedOnceForCurrentMonth() throws Exception {
    //    Given today is 2/1/2017
    Clock clock20170201 = TestClockSupport.createFixedClockOn(2017, 2, 1);

    // Given an account with $100
    Account account = new Account(new FakeTransactionRepository(), clock20170201);
    account.deposit(TestClockSupport.localDateTimeAtMidnightOf(2017, 1, 7), 10000, "initial deposit");

    // When account balance is requested (will generate interest credit for "today" = 2/1/2017)
    account.balance();

    // Then when we ask for it again, it should still have interested credited only once
    account.balance();

    LocalDateTime feb1of2017 = TestClockSupport.localDateTimeAtMidnightOf(2017, 2, 1);
    long numberFound = account.transactions()
                              .stream()
                              .filter(Transaction::isInterestCredit)
                              .filter(t -> t.dateTime().isEqual(feb1of2017))
                              .count();
    assertThat(numberFound)
        .isEqualTo(1);

  }

  @Test
  public void missingOneMonthCreditShouldAddOneMonthCredit() throws Exception {

    //    Given today is 2/1/2019
    Clock clock20190201 = TestClockSupport.createFixedClockOn(2019, 2, 1);
    Account account = new Account(new FakeTransactionRepository(), clock20190201);
    account.deposit(TestClockSupport.localDateTimeAtMidnightOf(2018, 11, 27), 10000, "initial deposit");
    //    And last interest credit was on 1/1/2019
    account.interestCredit(TestClockSupport.localDateTimeAtMidnightOf(2019, 1, 1), 27);

    //    When we request balance
    account.balance();

    //    Then an interest credit is added for 2/1/2019
    LocalDateTime feb1of2019 = TestClockSupport.localDateTimeAtMidnightOf(2019, 2, 1);
    boolean found = account.transactions()
                           .stream()
                           .filter(Transaction::isInterestCredit)
                           .anyMatch(t -> t.dateTime().isEqual(feb1of2019));
    assertThat(found)
        .isTrue();
  }

  @Test
  public void missingOneMonthCreditShouldAddOneMonthCreditEvenIfTodayIsNotTheFirstDayOfMonth() throws Exception {

    //    Given today is 2/27/2019
    Clock clock20190227 = TestClockSupport.createFixedClockOn(2019, 2, 27);
    Account account = new Account(new FakeTransactionRepository(), clock20190227);
    account.deposit(TestClockSupport.localDateTimeAtMidnightOf(2018, 11, 27), 20000, "initial deposit");
    //    And last interest credit was on 1/1/2019
    account.interestCredit(TestClockSupport.localDateTimeAtMidnightOf(2019, 1, 1), 33);

    //    When we request balance
    account.balance();

    //    Then an interest credit is added for 2/1/2019
    boolean found = account.transactions()
                           .stream()
                           .filter(Transaction::isInterestCredit)
                           .anyMatch(t -> t.dateTime().isEqual(TestClockSupport.localDateTimeAtMidnightOf(2019, 2, 1)));
    assertThat(found)
        .isTrue();
  }

  @Test
  public void retroactiveCreditShouldOnlyBeCreatedAsOfFirstAccountTransaction() throws Exception {
    //    Given today is 3/10/2019
    Clock clock20190310 = TestClockSupport.createFixedClockOn(2019, 3, 10);

    //    And account balance is $100
    Account account = new Account(new FakeTransactionRepository(), clock20190310);
    //    And first transaction was 2/3/2019
    account.deposit(TestClockSupport.localDateTimeAtMidnightOf(2019, 2, 3), 10000, "initial deposit");

    //    And no interest was ever credited (0 interest credited transactions)

    //    When we request balance (on 3/10/2019)
    account.balance();

    //    Then we add credit ONLY for 3/1/2019
    boolean found = account.transactions()
                           .stream()
                           .filter(Transaction::isInterestCredit)
                           .anyMatch(t -> t.dateTime().isEqual(TestClockSupport.localDateTimeAtMidnightOf(2019, 3, 1)));

    assertThat(found)
        .isTrue();
  }

  @Test
  public void monthsWithoutCreditsShouldFillInCreditsRetroactively() throws Exception {
    //    Given today is 2/28/2019
    Clock clock20190228 = TestClockSupport.createFixedClockOn(2019, 2, 28);

    //    And account has $200 balance
    Account account = new Account(new FakeTransactionRepository(), clock20190228);
    account.deposit(TestClockSupport.localDateTimeAtMidnightOf(2018, 10, 27), 20000, "initial deposit");

    //    And last interest credit was on 11/1/2018
    account.interestCredit(TestClockSupport.localDateTimeAtMidnightOf(2018, 11, 1), 33);

    //    When we request balance today (2/28/2019)
    account.balance();

    List<LocalDateTime> interestCreditDateTimes = account.transactions()
                                                         .stream()
                                                         .filter(Transaction::isInterestCredit)
                                                         .map(Transaction::dateTime)
                                                         .collect(Collectors.toList());

    //    Then we should have interest credits for 12/1/2018, 1/1/2019, 2/1/2019
    assertThat(interestCreditDateTimes)
        .containsExactlyInAnyOrder(
            TestClockSupport.localDateTimeAtMidnightOf(2018, 11, 1),
            TestClockSupport.localDateTimeAtMidnightOf(2018, 12, 1),
            TestClockSupport.localDateTimeAtMidnightOf(2019, 1, 1),
            TestClockSupport.localDateTimeAtMidnightOf(2019, 2, 1));
  }

  @Test
  public void interestCreditAmountsShouldBeBasedOnBalanceAsOfCreditDate() throws Exception {
    //    Given today is 2/28/2019
    Clock clock20190228 = TestClockSupport.createFixedClockOn(2019, 2, 28);
    Account account = new Account(new FakeTransactionRepository(), clock20190228);

    //    And a deposit of $100 was made on 12/1/2018
    account.deposit(TestClockSupport.localDateTimeAtMidnightOf(2018, 12, 1), 10000, "initial deposit");
    //    And another deposit of $200 was made on 1/15/2019
    account.deposit(TestClockSupport.localDateTimeAtMidnightOf(2019, 1, 15), 20000, "second deposit");

    //    When we request balance
    account.balance();

    //    Then we add interest credits of 1/1/2018 (bal=$100/21c), 2/1/2018 (bal=$300.21/63c)
    List<Integer> transactions = account.transactions()
                                        .stream()
                                        .filter(Transaction::isInterestCredit)
                                        .map(Transaction::amount)
                                        .collect(Collectors.toList());

    assertThat(transactions)
        .containsExactlyInAnyOrder(21, 63);
  }

  @Test
  public void interestCreditShouldIncludePreviousInterestCredits() throws Exception {
    Clock clock20190228 = TestClockSupport.createFixedClockOn(2019, 1, 28);
    Account account = new Account(new FakeTransactionRepository(), clock20190228);

    account.deposit(TestClockSupport.localDateTimeAtMidnightOf(2018, 10, 31), 5000000, "initial deposit");

    account.balance();

    List<Integer> transactions = account.transactions()
                                        .stream()
                                        .sorted(comparing(Transaction::dateTime))
                                        .filter(Transaction::isInterestCredit)
                                        .map(Transaction::amount)
                                        .collect(Collectors.toList());

    assertThat(transactions)
        .containsExactly(10417, 10438, 10460);

  }

}