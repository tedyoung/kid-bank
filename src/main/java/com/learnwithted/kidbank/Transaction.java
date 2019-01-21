package com.learnwithted.kidbank;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@EqualsAndHashCode
@ToString
public class Transaction {
  private final LocalDateTime date;
  private final String action;
  private final int amount; // scaled two decimal places, i.e., cents
  private final String source;

  public Transaction(LocalDateTime date, String action, int amount, String source) {
    this.date = date;
    this.action = action;
    this.amount = amount;
    this.source = source;
  }

  public LocalDateTime dateTime() {
    return date;
  }

  public String action() {
    return action;
  }

  public int amount() {
    return amount;
  }

  public String source() {
    return source;
  }
}
