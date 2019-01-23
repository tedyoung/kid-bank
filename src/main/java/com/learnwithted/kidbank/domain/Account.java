package com.learnwithted.kidbank.domain;

import com.google.common.collect.ImmutableSet;

import java.time.LocalDateTime;
import java.util.List;

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

  private void addNewTransaction(Transaction transaction) {
    transactions = ImmutableSet.<Transaction>builder()
                       .addAll(transactions)
                       .add(transaction)
                       .build();
  }

  public ImmutableSet<Transaction> transactions() {
    return transactions;
  }

  public void load(List<Transaction> transactionsToLoad) {
    transactions = ImmutableSet.<Transaction>builder()
                       .addAll(transactions)
                       .addAll(transactionsToLoad)
                       .build();
  }
}
