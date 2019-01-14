package com.learnwithted.kidbank;

import java.util.Collections;
import java.util.Set;

public class Account {
  private int balance = 0;

  public int balance() {
    return balance;
  }

  public void deposit(int amount) {
    balance += amount;
  }

  public Set<Transaction> transactions() {
    return Collections.EMPTY_SET;
  }
}
