package com.learnwithted.kidbank.domain;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.learnwithted.kidbank.domain.TestClockSupport.createFixedClockOn;
import static com.learnwithted.kidbank.domain.TestClockSupport.localDateTimeAtMidnightOf;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;

public class InterestCreditTest {

  private static final FakeTransactionRepository FAKE_TRANSACTION_REPOSITORY = new FakeTransactionRepository();

  @Test
  public void onFirstOfMonthInterestShouldBeDepositedIntoAccount() throws Exception {
    Clock clock20170201 = createFixedClockOn(2017, 2, 1);

    // Given an account with $100
    Account account = new Account(FAKE_TRANSACTION_REPOSITORY, clock20170201);
    account.deposit(localDateTimeAtMidnightOf(2016, 12, 7), 10000, "initial deposit");

    // Then $100 * (2.5% / 12) should be credited into the account, twice: for 1/1/17 & 2/1/17
    assertThat(account.balance())
        .isEqualTo(10000 + 21 + 21); // rounding up 20.8333 to 21 cents
  }

  @Test
  public void interestShouldOnlyBeDepositedOnceForCurrentMonth() throws Exception {
    //    Given today is 2/1/2017
    Clock clock20170201 = createFixedClockOn(2017, 2, 1);

    // Given an account with $100
    Account account = new Account(FAKE_TRANSACTION_REPOSITORY, clock20170201);
    account.deposit(localDateTimeAtMidnightOf(2017, 1, 7), 10000, "initial deposit");

    // When account balance is requested (will generate interest credit for "today" = 2/1/2017)
    account.balance();

    // Then when we ask for it again, it should still have interested credited only once
    account.balance();

    LocalDateTime feb1of2017 = localDateTimeAtMidnightOf(2017, 2, 1);
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
    Clock clock20190201 = createFixedClockOn(2019, 2, 1);
    Account account = new Account(FAKE_TRANSACTION_REPOSITORY, clock20190201);
    account.deposit(localDateTimeAtMidnightOf(2018, 11, 27), 10000, "initial deposit");
    //    And last interest credit was on 1/1/2019
    account.interestCredit(localDateTimeAtMidnightOf(2019, 1, 1), 27);

    //    When we request balance
    account.balance();

    //    Then an interest credit is added for 2/1/2019
    LocalDateTime feb1of2019 = localDateTimeAtMidnightOf(2019, 2, 1);
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
    Clock clock20190227 = createFixedClockOn(2019, 2, 27);
    Account account = new Account(FAKE_TRANSACTION_REPOSITORY, clock20190227);
    account.deposit(localDateTimeAtMidnightOf(2018, 11, 27), 20000, "initial deposit");
    //    And last interest credit was on 1/1/2019
    account.interestCredit(localDateTimeAtMidnightOf(2019, 1, 1), 33);

    //    When we request balance
    account.balance();

    //    Then an interest credit is added for 2/1/2019
    boolean found = account.transactions()
                           .stream()
                           .filter(Transaction::isInterestCredit)
                           .anyMatch(t -> t.dateTime().isEqual(localDateTimeAtMidnightOf(2019, 2, 1)));
    assertThat(found)
        .isTrue();
  }

  @Test
  public void retroactiveCreditShouldOnlyBeCreatedAsOfFirstAccountTransaction() throws Exception {
    //    Given today is 3/10/2019
    Clock clock20190310 = createFixedClockOn(2019, 3, 10);

    //    And account balance is $100
    Account account = new Account(FAKE_TRANSACTION_REPOSITORY, clock20190310);
    //    And first transaction was 2/3/2019
    account.deposit(localDateTimeAtMidnightOf(2019, 2, 3), 10000, "initial deposit");

    //    And no interest was ever credited (0 interest credited transactions)

    //    When we request balance (on 3/10/2019)
    account.balance();

    //    Then we add credit ONLY for 3/1/2019
    boolean found = account.transactions()
                           .stream()
                           .filter(Transaction::isInterestCredit)
                           .anyMatch(t -> t.dateTime().isEqual(localDateTimeAtMidnightOf(2019, 3, 1)));

    assertThat(found)
        .isTrue();
  }

  @Test
  public void monthsWithoutCreditsShouldFillInCreditsRetroactively() throws Exception {
    //    Given today is 2/28/2019
    Clock clock20190228 = createFixedClockOn(2019, 2, 28);

    //    And account has $200 balance
    Account account = new Account(FAKE_TRANSACTION_REPOSITORY, clock20190228);
    account.deposit(localDateTimeAtMidnightOf(2018, 10, 27), 20000, "initial deposit");

    //    And last interest credit was on 11/1/2018
    account.interestCredit(localDateTimeAtMidnightOf(2018, 11, 1), 33);

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
            localDateTimeAtMidnightOf(2018, 11, 1),
            localDateTimeAtMidnightOf(2018, 12, 1),
            localDateTimeAtMidnightOf(2019, 1, 1),
            localDateTimeAtMidnightOf(2019, 2, 1));
  }

  @Test
  public void interestCreditAmountsShouldBeBasedOnBalanceAsOfCreditDate() throws Exception {
    //    Given today is 2/28/2019
    Clock clock = createFixedClockOn(2019, 2, 28);
    Account account = new Account(FAKE_TRANSACTION_REPOSITORY, clock);

    //    And a deposit of $100 was made on 12/1/2018
    account.deposit(localDateTimeAtMidnightOf(2018, 12, 1), 100_00, "initial deposit");
    //    And another deposit of $200 was made on 1/15/2019
    account.deposit(localDateTimeAtMidnightOf(2019, 1, 15), 200_00, "second deposit");

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
    Clock clock = createFixedClockOn(2019, 1, 28);
    Account account = new Account(FAKE_TRANSACTION_REPOSITORY, clock);

    account.deposit(localDateTimeAtMidnightOf(2018, 10, 31), 5000000, "initial deposit");

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

  @Test
  public void interestCreditForAccountWithZeroBalanceShouldNotCreditAnyInterest() throws Exception {
    Clock clock = createFixedClockOn(2019, 1, 28);
    Account account = new Account(FAKE_TRANSACTION_REPOSITORY, clock);

    account.deposit(localDateTimeAtMidnightOf(2018, 10, 31), 500_00, "initial deposit");
    account.spend(localDateTimeAtMidnightOf(2018, 10, 31), 500_00, "spend all the moneeze");

    assertThat(account.balance())
        .isZero();
  }

  @Test
  public void interestCreditForAccountWithNegativeBalanceShouldNotCreditAnyInterest() throws Exception {
    Clock clock = createFixedClockOn(2019, 1, 28);
    Account account = new Account(FAKE_TRANSACTION_REPOSITORY, clock);

    account.spend(localDateTimeAtMidnightOf(2018, 10, 31), 500_00, "spend money I don't have");

    assertThat(account.balance())
        .isEqualTo(-500_00);
  }

  @Test
  public void negativeBalanceShouldResultInZeroAmountInterestCreditTransactions() throws Exception {
    Clock clock = createFixedClockOn(2019, 3, 2);
    Account account = new Account(FAKE_TRANSACTION_REPOSITORY, clock);

    account.spend(localDateTimeAtMidnightOf(2019, 1, 30), 500_00, "spend money I don't have");

    // trigger interest credit calculation
    account.balance();

    assertThat(account.transactions()
                      .stream()
                      .filter(Transaction::isInterestCredit))
        .hasSize(2);

  }

}