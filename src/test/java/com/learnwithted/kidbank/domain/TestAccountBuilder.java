package com.learnwithted.kidbank.domain;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;

import java.time.Clock;

import static com.learnwithted.kidbank.domain.TestClockSupport.localDateTimeAtMidnightOf;
import static java.time.LocalDateTime.now;

public class TestAccountBuilder {

  private TransactionRepository transactionRepository = new FakeTransactionRepository();
  private BalanceChangedNotifier balanceChangedNotifier = new StubBalanceChangeNotifier();
  private InterestStrategy interestStrategy = (account) -> {};
  private Clock clock = Clock.systemDefaultZone();

  public static TestAccountBuilder builder() {
    return new TestAccountBuilder();
  }

  public Account build() {
    return new Account(transactionRepository, balanceChangedNotifier, interestStrategy);
  }

  public TestAccountBuilder notifier(BalanceChangedNotifier balanceChangedNotifier) {
    this.balanceChangedNotifier = balanceChangedNotifier;
    return this;
  }

  public TestAccountBuilder initialBalanceOf(int amount) {
    transactionRepository.save(Transaction.createDeposit(now(), amount, "initialized in test"));
    return this;
  }

  public TestAccountBuilder clockOf(int year, int month, int day) {
    clock = TestClockSupport.createFixedClockOn(year, month, day);
    return this;
  }

  public TestAccountBuilder initialBalanceOf(int amount, int year, int month, int day) {
    transactionRepository.save(Transaction.createDeposit(
        localDateTimeAtMidnightOf(year, month, day), amount, "initialized in test"));
    return this;
  }

  public TestAccountBuilder monthlyInterestStrategy() {
    interestStrategy = new MonthlyInterestStrategy(clock);
    return this;
  }

  public TransactionRepository transactionRepository() {
    return transactionRepository;
  }
}
