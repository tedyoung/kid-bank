package com.learnwithted.kidbank.domain;

import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class MonthlyInterestStrategy implements InterestStrategy {
  public static final double INTEREST_RATE_PER_MONTH = (0.025 / 12);

  private final Clock clock;

  public MonthlyInterestStrategy(Clock clock) {
    this.clock = clock;
  }

  @Override
  public void creditInterestAsNeeded(Account account) {
    // get month/year dates for retroactive credits
    Optional<LocalDateTime> mostRecentTransactionDateTime =
        mostRecentInterestCreditDateTime(account.transactions());

    if (!mostRecentTransactionDateTime.isPresent()) {
      return;
    }

    // loop from recent interest credit date to first of current clock's month
    LocalDateTime lastDateTimeForCredit = firstDayOfNowMonth();

    LocalDateTime creditDateTime = firstDayOfMonthAfter(mostRecentTransactionDateTime.get());
    while (!creditDateTime.isAfter(lastDateTimeForCredit)) {
      int interestToCredit = calculateInterestFor(creditDateTime, account.transactions());
      account.interestCredit(creditDateTime, interestToCredit);
      creditDateTime = creditDateTime.plusMonths(1);
    }
  }


  private LocalDateTime firstDayOfMonthAfter(LocalDateTime localDateTime) {
    return localDateTime.plusMonths(1).withDayOfMonth(1);
  }

  private int calculateInterestFor(LocalDateTime creditDateTime,
                                  ImmutableSet<Transaction> transactions) {
    int balanceThruCreditDate = transactions
                                    .stream()
                                    .filter(t -> t.dateTime().isBefore(creditDateTime))
                                    .mapToInt(Transaction::signedAmount)
                                    .sum();
    return calculateInterest(balanceThruCreditDate);

  }

  private int calculateInterest(int currentBalance) {
    if (currentBalance <= 0) {
      return 0;
    }
    // Calculates interest credit with rounding up
    return (int) (currentBalance * INTEREST_RATE_PER_MONTH + 0.5);
  }


  private Optional<LocalDateTime> mostRecentInterestCreditDateTime(
      ImmutableSet<Transaction> transactions) {
    // most recent interest credit date
    Optional<LocalDateTime> mostRecentInterestCreditTransaction
        = transactions.stream()
                      .filter(Transaction::isInterestCredit)
                      .map(Transaction::dateTime)
                      .max(LocalDateTime::compareTo);

    Optional<LocalDateTime> firstTransactionDateTime = transactions.stream()
                                                                   .map(Transaction::dateTime)
                                                                   .min(LocalDateTime::compareTo);
    if (mostRecentInterestCreditTransaction.isPresent()) {
      return mostRecentInterestCreditTransaction;
    } else {
      return firstTransactionDateTime;
    }
  }

  private LocalDateTime firstDayOfNowMonth() {
    return LocalDateTime.now(clock).withDayOfMonth(1);
  }

}
