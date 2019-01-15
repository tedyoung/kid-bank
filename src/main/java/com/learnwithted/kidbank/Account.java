package com.learnwithted.kidbank;

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
    transactions = ImmutableSet.<Transaction>builder()
                       .addAll(transactions)
                       .add(new Transaction(transactionDateTime, "Deposit", amount, source))
                       .build();
  }

  public ImmutableSet<Transaction> transactions() {
    return transactions;
  }
}
