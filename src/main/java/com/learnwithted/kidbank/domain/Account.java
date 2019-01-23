package com.learnwithted.kidbank.domain;

import com.google.common.collect.ImmutableSet;

import java.time.LocalDateTime;

public class Account {
  private int balance = 0;
  private ImmutableSet<Transaction> transactions = ImmutableSet.of();

  public int balance() {
    return balance;
  }

  public void deposit(LocalDateTime transactionDateTime, int amount, String source) {
    balance += amount;
    addNewTransaction("Deposit", amount, source, transactionDateTime);
  }

  public void spend(LocalDateTime transactionDateTime, int amount, String description) {
    balance -= amount;
    addNewTransaction("Spend", amount, description, transactionDateTime);
  }

  public void addNewTransaction(String action, int amount, String source, LocalDateTime transactionDateTime) {
    transactions = ImmutableSet.<Transaction>builder()
                       .addAll(transactions)
                       .add(new Transaction(transactionDateTime, action, amount, source))
                       .build();
  }

  public ImmutableSet<Transaction> transactions() {
    return transactions;
  }
}
