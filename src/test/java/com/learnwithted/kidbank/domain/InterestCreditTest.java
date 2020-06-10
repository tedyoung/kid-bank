package com.learnwithted.kidbank.domain;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.learnwithted.kidbank.domain.TestClockSupport.localDateTimeAtMidnightOf;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.*;

public class InterestCreditTest {

  @Test
  public void onFirstOfMonthInterestShouldBeDepositedIntoAccount() throws Exception {

    // Given an account with $100
    Account account = TestAccountBuilder.builder()
                                        .initialBalanceOf(100_00, 2016, 12, 7)
                                        .buildAsInterestEarning(2017, 2, 1);

    // Then $100 * (2.5% / 12) should be credited into the account, twice: for 1/1/17 & 2/1/17
    assertThat(account.interestEarned())
        .isEqualTo(21 + 21);

    assertThat(account.balance())
        .isEqualTo(100_00 + 21 + 21); // rounding up 20.8333 to 21 cents
  }

  @Test
  public void interestShouldOnlyBeDepositedOnceForCurrentMonth() throws Exception {
    //    Given today is 2/1/2017
    //    and Given an account with $100
    Account account = TestAccountBuilder.builder()
                                        .initialBalanceOf(100_00, 2017, 1, 7)
                                        .buildAsInterestEarning(2017, 2, 1);

    // When account balance is requested (will generate interest credit for "today" = 2/1/2017)
    account.balance();

    // Then when we ask for it again, it should still have interested credited only once
    account.balance();

    LocalDateTime feb1of2017 = localDateTimeAtMidnightOf(2017, 2, 1);
    long numberFound = account.transactions()
                              .stream()
                              .filter(Transaction.isInterestCredit())
                              .filter(t -> t.dateTime().isEqual(feb1of2017))
                              .count();
    assertThat(numberFound)
        .isEqualTo(1);

  }

  @Test
  public void retroactiveCreditShouldOnlyBeCreatedAsOfFirstAccountTransaction() throws Exception {
    //    Given today is 3/10/2019
    //    And account balance is $100
    Account account = TestAccountBuilder.builder()
                                        .initialBalanceOf(100_00, 2019, 2, 3)
                                        .buildAsInterestEarning(2019, 3, 10);

    //    Then we add credit ONLY for 3/1/2019
    boolean found = account.transactions()
                           .stream()
                           .filter(Transaction.isInterestCredit())
                           .anyMatch(t -> t.dateTime().isEqual(localDateTimeAtMidnightOf(2019, 3, 1)));

    assertThat(found)
        .isTrue();
  }

  @Test
  public void interestCreditAmountsShouldBeBasedOnBalanceAsOfCreditDate() throws Exception {
    //    Given today is 2/28/2019
    //    And a deposit of $100 was made on 12/1/2018
    Account account = TestAccountBuilder.builder()
                                        .initialBalanceOf(100_00, 2018, 12, 1)
                                        .buildAsInterestEarning(2019, 2, 28);

    //    And another deposit of $200 was made on 1/15/2019
    account.deposit(localDateTimeAtMidnightOf(2019, 1, 15), 200_00, "second deposit", new DummyUserProfile());

    //    Then we add interest credits of 1/1/2018 (bal=$100/21c), 2/1/2018 (bal=$300.21/63c)
    List<Integer> transactions = account.transactions()
                                        .stream()
                                        .filter(Transaction.isInterestCredit())
                                        .map(Transaction::amount)
                                        .collect(Collectors.toList());

    assertThat(transactions)
        .containsExactlyInAnyOrder(21, 63);
  }

  @Test
  public void interestCreditShouldIncludePreviousInterestCredits() throws Exception {
    Account account = TestAccountBuilder.builder()
                                        .initialBalanceOf(50_000_00, 2018, 10, 31)
                                        .buildAsInterestEarning(2019, 1, 28);

    List<Integer> transactions = account.transactions()
                                        .stream()
                                        .sorted(comparing(Transaction::dateTime))
                                        .filter(Transaction.isInterestCredit())
                                        .map(Transaction::amount)
                                        .collect(Collectors.toList());

    assertThat(transactions)
        .containsExactly(10417, 10438, 10460);

  }

  @Test
  public void interestCreditForAccountWithZeroBalanceShouldNotCreditAnyInterest() throws Exception {
    Account account = TestAccountBuilder.builder()
                                        .initialBalanceOf(500_00, 2018, 10, 31)
                                        .buildAsInterestEarning(2019, 1, 28);

    account.spend(localDateTimeAtMidnightOf(2018, 10, 31), 500_00, "spend all the moneeze", new DummyUserProfile());

    assertThat(account.interestEarned())
        .isZero();

    assertThat(account.balance())
        .isZero();
  }

  @Test
  public void interestCreditForAccountWithNegativeBalanceShouldNotCreditAnyInterest() throws Exception {
    Account account = TestAccountBuilder.builder()
                                        .buildAsInterestEarning(2019, 1, 28);

    account.spend(localDateTimeAtMidnightOf(2018, 10, 31), 500_00, "spend money I don't have", new DummyUserProfile());

    assertThat(account.balance())
        .isEqualTo(-500_00);
  }

  @Test
  public void negativeBalanceShouldResultInZeroAmountInterestCreditTransactions() throws Exception {
    Account account = TestAccountBuilder.builder()
                                        .buildAsInterestEarning(2019, 3, 2);

    account.spend(localDateTimeAtMidnightOf(2019, 1, 30), 500_00, "spend money I don't have", new DummyUserProfile());

    assertThat(account.transactions()
                      .stream()
                      .filter(Transaction.isInterestCredit()))
        .hasSize(2);
  }

}