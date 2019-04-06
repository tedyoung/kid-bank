package com.learnwithted.kidbank.domain;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;

import static com.learnwithted.kidbank.domain.TestClockSupport.createFixedClockOn;
import static com.learnwithted.kidbank.domain.TestClockSupport.localDateTimeAtMidnightOf;
import static java.time.LocalDateTime.now;

public class TestAccountBuilder {

  private TransactionRepository transactionRepository = new FakeTransactionRepository();
  private BalanceChangedNotifier balanceChangedNotifier = new StubBalanceChangeNotifier();
  private InterestStrategy interestStrategy = account -> {};
  private GoalRepository goalRepository = new StubGoalRepository();
  private CoreAccount coreAccount;

  public static TestAccountBuilder builder() {
    return new TestAccountBuilder();
  }

  public InterestEarningAccount buildAsInterestEarning(int year, int month, int day) {
    interestStrategy = new MonthlyInterestStrategy(createFixedClockOn(year, month, day));
    return new InterestEarningAccount(buildAsCore(), interestStrategy);
  }

  public CoreAccount buildAsCore() {
    coreAccount = new CoreAccount(transactionRepository, goalRepository, balanceChangedNotifier);
    return coreAccount;
  }

  public CoreAccount coreAccount() {
    if (coreAccount == null) {
      throw new IllegalStateException("Account has not been built yet, use buildAsCore() or buildAsInterestEarning() first.");
    }
    return coreAccount;
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

  public TransactionRepository transactionRepository() {
    return transactionRepository;
  }

  public TestAccountBuilder withGoalRepository(GoalRepository goalRepository) {
    this.goalRepository = goalRepository;
    return this;
  }

  public InterestStrategy interestStrategy() {
    return interestStrategy;
  }
}
