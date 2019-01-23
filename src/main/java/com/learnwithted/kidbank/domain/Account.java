package com.learnwithted.kidbank.domain;

import com.google.common.collect.ImmutableSet;

import java.time.LocalDateTime;

public class Account {
  private ImmutableSet<Transaction> transactions = ImmutableSet.of();

  public int balance() {
    return transactions.stream()
        .mapToInt(Transaction::signedAmount)
        .sum();
  }

  public void deposit(LocalDateTime transactionDateTime, int amount, String source) {
    Transaction deposit = Transaction.createDeposit(transactionDateTime, amount, source);
    addNewTransaction(deposit);
  }

  public void spend(LocalDateTime transactionDateTime, int amount, String description) {
    Transaction spend = Transaction.createSpend(transactionDateTime, amount, description);
    addNewTransaction(spend);
  }

  public void addNewTransaction(Transaction transaction) {
    transactions = ImmutableSet.<Transaction>builder()
                       .addAll(transactions)
                       .add(transaction)
                       .build();
  }

  public ImmutableSet<Transaction> transactions() {
    return transactions;
  }
}
