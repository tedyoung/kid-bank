package com.learnwithted.kidbank.domain;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;

import static com.learnwithted.kidbank.domain.TestClockSupport.createFixedClockOn;
import static com.learnwithted.kidbank.domain.TestClockSupport.localDateTimeAtMidnightOf;
import static java.time.LocalDateTime.now;

public class TestAccountBuilder {

  private TransactionRepository transactionRepository = new FakeTransactionRepository();
  private BalanceChangedNotifier balanceChangedNotifier = new StubBalanceChangeNotifier();
  private InterestStrategy interestStrategy = (account) -> {};

  public static TestAccountBuilder builder() {
    return new TestAccountBuilder();
  }

  public InterestEarningAccount build() {
    CoreAccount coreAccount = new CoreAccount(transactionRepository, balanceChangedNotifier);
    return new InterestEarningAccount(coreAccount, interestStrategy);
  }

  public TestAccountBuilder notifier(BalanceChangedNotifier balanceChangedNotifier) {
    this.balanceChangedNotifier = balanceChangedNotifier;
    return this;
  }

  public TestAccountBuilder initialBalanceOf(int amount) {
    transactionRepository.save(Transaction.createDeposit(now(), amount, "initialized in test"));
    return this;
  }

  public TestAccountBuilder initialBalanceOf(int amount, int year, int month, int day) {
    transactionRepository.save(Transaction.createDeposit(
        localDateTimeAtMidnightOf(year, month, day), amount, "initialized in test"));
    return this;
  }

  public TestAccountBuilder withMonthlyInterestStrategyAsOf(int year, int month, int day) {
    interestStrategy = new MonthlyInterestStrategy(createFixedClockOn(year, month, day));
    return this;
  }

  public InterestStrategy interestStrategy() {
    return interestStrategy;
  }

  public TransactionRepository transactionRepository() {
    return transactionRepository;
  }
}
