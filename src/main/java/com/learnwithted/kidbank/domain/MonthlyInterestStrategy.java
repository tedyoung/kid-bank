package com.learnwithted.kidbank.domain;

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
  public void createInterestCreditTransactionsFor(InterestEarningAccount account) {
    Optional<LocalDateTime> firstTransactionDateTime = firstTransactionDateTime(account);

    if (!firstTransactionDateTime.isPresent()) {
      return;
    }

    LocalDateTime creditDateTime = firstDayOfMonthAfter(firstTransactionDateTime.get());

    LocalDateTime lastDateTimeForCredit = firstDayOfNowMonth();

    // loop through first of current clock's month
    while (!creditDateTime.isAfter(lastDateTimeForCredit)) {
      int interestToCredit = calculateInterest(account.balanceUpTo(creditDateTime));
      account.interestCredit(creditDateTime, interestToCredit);
      creditDateTime = creditDateTime.plusMonths(1);
    }
  }

  private Optional<LocalDateTime> firstTransactionDateTime(InterestEarningAccount account) {
    return account.transactions().stream()
                  .filter(Transaction.isInterestCredit().negate())
                  .map(Transaction::dateTime)
                  .min(LocalDateTime::compareTo);
  }


  private LocalDateTime firstDayOfMonthAfter(LocalDateTime localDateTime) {
    return localDateTime.plusMonths(1).withDayOfMonth(1);
  }

  private int calculateInterest(int currentBalance) {
    if (currentBalance <= 0) {
      return 0;
    }
    // Calculates interest credit with rounding up
    return (int) (currentBalance * INTEREST_RATE_PER_MONTH + 0.5);
  }


  private LocalDateTime firstDayOfNowMonth() {
    // TODO - refactor candidate: replace clock by passing the "now" date when calling into this class
    return LocalDateTime.now(clock).withDayOfMonth(1);
  }

}
