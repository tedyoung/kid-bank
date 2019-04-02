package com.learnwithted.kidbank.domain;

import com.google.common.collect.ImmutableList;

import java.time.LocalDateTime;
import java.util.List;

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
  }


  public void interestCredit(LocalDateTime localDateTime, int interestAmount) {
    Transaction interestCreditTransaction = Transaction.createInterestCredit(localDateTime, interestAmount);
    interestTransactions = ImmutableList.<Transaction>builder()
                               .addAll(interestTransactions)
                               .add(interestCreditTransaction)
                               .build();
  }


  @Override
  public int balance() {
    interestStrategy.creditInterestAsNeeded(this);
    return balanceUpTo(LocalDateTime.MAX);
  }

  @Override
  public void deposit(LocalDateTime transactionDateTime, int amount, String source) {
    decorateeAccount.deposit(transactionDateTime, amount, source);
  }

  @Override
  public void spend(LocalDateTime transactionDateTime, int amount, String description) {
    decorateeAccount.spend(transactionDateTime, amount, description);
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
    decorateeAccount.load(transactionsToLoad);
  }

  @Override
  public int balanceUpTo(LocalDateTime localDateTime) {
    return decorateeAccount.balanceUpTo(localDateTime)
               + interestTotalUpTo(localDateTime);
  }
}
