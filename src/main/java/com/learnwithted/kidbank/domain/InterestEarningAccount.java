package com.learnwithted.kidbank.domain;

import com.google.common.collect.ImmutableList;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Decorator (thanks to flamaddidle84)
 */
public class InterestEarningAccount implements Account {
  private final Account decorateeAccount;

  private final InterestStrategy interestStrategy;
  private ImmutableList<Transaction> interestTransactions = ImmutableList.of();

  public InterestEarningAccount(Account decorateeAccount, InterestStrategy interestStrategy) {
    this.decorateeAccount = decorateeAccount;
    this.interestStrategy = interestStrategy;
    computeInterest();
  }


  public void interestCredit(LocalDateTime localDateTime, int interestAmount) {
    Transaction interestCreditTransaction =
        Transaction.createInterestCredit(localDateTime, interestAmount);
    interestTransactions = ImmutableList.<Transaction>builder()
                               .addAll(interestTransactions)
                               .add(interestCreditTransaction)
                               .build();
  }

  @Override
  public int balance() {
    return balanceUpTo(LocalDateTime.MAX);
  }

  @Override
  public int interestEarned() {
    return interestTotalUpTo(LocalDateTime.MAX);
  }

  private void computeInterest() {
    // throw away any existing interest credit transactions
    interestTransactions = ImmutableList.of();
    // (re)compute the interest credits
    interestStrategy.createInterestCreditTransactionsFor(this);
  }

  @Override
  public void deposit(LocalDateTime transactionDateTime, int amount, String source, UserProfile userProfile) {
    decorateeAccount.deposit(transactionDateTime, amount, source, userProfile);
    computeInterest();
  }

  @Override
  public void spend(LocalDateTime transactionDateTime, int amount, String description, UserProfile userProfile) {
    decorateeAccount.spend(transactionDateTime, amount, description, userProfile);
    computeInterest();
  }

  @Override
  public ImmutableList<Transaction> transactions() {
    return ImmutableList.<Transaction>builder()
               .addAll(decorateeAccount.transactions())
               .addAll(interestTransactions)
               .build();
  }

  private int interestTotalUpTo(LocalDateTime localDateTime) {
    return interestTransactions.stream()
                               .filter(t -> t.dateTime().isBefore(localDateTime))
                               .mapToInt(Transaction::signedAmount)
                               .sum();
  }

  @Override
  public void load(List<Transaction> transactionsToLoad) {
    transactionsToLoad.removeIf(Transaction.isInterestCredit());
    decorateeAccount.load(transactionsToLoad);
    computeInterest();
  }

  @Override
  public int balanceUpTo(LocalDateTime localDateTime) {
    return decorateeAccount.balanceUpTo(localDateTime)
               + interestTotalUpTo(localDateTime);
  }

  @Override
  public Set<Goal> goals() {
    return decorateeAccount.goals();
  }

  @Override
  public void createGoal(String description, int targetAmount) {
    decorateeAccount.createGoal(description, targetAmount);
  }
}
