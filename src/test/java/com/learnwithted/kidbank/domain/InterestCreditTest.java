package com.learnwithted.kidbank.domain;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.learnwithted.kidbank.domain.TestClockSupport.localDateTimeAtMidnightOf;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;

public class InterestCreditTest {

  private static final FakeTransactionRepository FAKE_TRANSACTION_REPOSITORY = new FakeTransactionRepository();

  @Test
  public void onFirstOfMonthInterestShouldBeDepositedIntoAccount() throws Exception {

    // Given an account with $100
    Account account = TestAccountBuilder.builder()
                                        .initialBalanceOf(100_00, 2016, 12, 7)
                                        .withMonthlyInterestStrategyAsOf(2017, 2, 1)
                                        .buildAsInterestEarning();

    // Then $100 * (2.5% / 12) should be credited into the account, twice: for 1/1/17 & 2/1/17
    assertThat(account.balance())
        .isEqualTo(10000 + 21 + 21); // rounding up 20.8333 to 21 cents
  }

  @Test
  public void interestShouldOnlyBeDepositedOnceForCurrentMonth() throws Exception {
    //    Given today is 2/1/2017
    //    and Given an account with $100
    Account account = TestAccountBuilder.builder()
                                        .initialBalanceOf(100_00, 2017, 1, 7)
                                        .withMonthlyInterestStrategyAsOf(2017, 2, 1)
                                        .buildAsInterestEarning();

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
    InterestEarningAccount account = TestAccountBuilder.builder()
                                                       .withMonthlyInterestStrategyAsOf(2019, 2, 1)
                                                       .initialBalanceOf(100_00, 2018, 11, 27)
                                                       .buildAsInterestEarning();

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
    InterestEarningAccount account = TestAccountBuilder
                                         .builder()
                                         .withMonthlyInterestStrategyAsOf(2019, 2, 27)
                                         .initialBalanceOf(200_00, 2018, 11, 27)
                                         .buildAsInterestEarning();

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
    //    And account balance is $100
    Account account = TestAccountBuilder.builder()
                                        .initialBalanceOf(100_00, 2019, 2, 3)
                                        .withMonthlyInterestStrategyAsOf(2019, 3, 10)
                                        .buildAsInterestEarning();

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
    //    And account has $200 balance
    InterestEarningAccount account = TestAccountBuilder
                                         .builder()
                                         .withMonthlyInterestStrategyAsOf(2019, 2, 28)
                                         .initialBalanceOf(200_00, 2018, 10, 27)
                                         .buildAsInterestEarning();

    //    And last interest credit was on 11/1/2018
    account.interestCredit(localDateTimeAtMidnightOf(2018, 11, 1), 33);

    //    When we request balance today (2/28/2019)
    account.balance();

    List<LocalDateTime> interestCreditDateTimes =
        account.transactions()
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
    //    And a deposit of $100 was made on 12/1/2018
    Account account = TestAccountBuilder.builder()
                                        .initialBalanceOf(100_00, 2018, 12, 1)
                                        .withMonthlyInterestStrategyAsOf(2019, 2, 28)
                                        .buildAsInterestEarning();

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
    Account account = TestAccountBuilder.builder()
                                        .withMonthlyInterestStrategyAsOf(2019, 1, 28)
                                        .initialBalanceOf(50_000_00, 2018, 10, 31)
                                        .buildAsInterestEarning();

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
    Account account = TestAccountBuilder.builder()
                                        .withMonthlyInterestStrategyAsOf(2019, 1, 28)
                                        .initialBalanceOf(500_00, 2018, 10, 31)
                                        .buildAsInterestEarning();

    account.spend(localDateTimeAtMidnightOf(2018, 10, 31), 500_00, "spend all the moneeze");

    assertThat(account.balance())
        .isZero();
  }

  @Test
  public void interestCreditForAccountWithNegativeBalanceShouldNotCreditAnyInterest() throws Exception {
    Account account = TestAccountBuilder.builder()
                                        .withMonthlyInterestStrategyAsOf(2019, 1, 28)
                                        .buildAsInterestEarning();

    account.spend(localDateTimeAtMidnightOf(2018, 10, 31), 500_00, "spend money I don't have");

    assertThat(account.balance())
        .isEqualTo(-500_00);
  }

  @Test
  public void negativeBalanceShouldResultInZeroAmountInterestCreditTransactions() throws Exception {
    Account account = TestAccountBuilder.builder()
                                        .withMonthlyInterestStrategyAsOf(2019, 3, 2)
                                        .buildAsInterestEarning();

    account.spend(localDateTimeAtMidnightOf(2019, 1, 30), 500_00, "spend money I don't have");

    // trigger interest credit calculation
    account.balance();

    assertThat(account.transactions()
                      .stream()
                      .filter(Transaction::isInterestCredit))
        .hasSize(2);

  }

}