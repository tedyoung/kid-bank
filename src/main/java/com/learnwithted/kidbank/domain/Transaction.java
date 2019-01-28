package com.learnwithted.kidbank.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@EqualsAndHashCode
@ToString
@Entity
@NoArgsConstructor
public class Transaction {
  private static final String SPEND = "Spend";
  private static final String DEPOSIT = "Deposit";
  private static final String INTEREST = "Interest Credit";

  // EXTRINSIC property used by Repository
  @Getter
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  // INTRINSIC properties of Transaction
  private LocalDateTime date;
  private String action;
  private int amount; // scaled two decimal places, i.e., cents
  private String source;

  public Transaction(LocalDateTime date, String action, int amount, String source) {
    this.date = date;
    this.action = action;
    this.amount = amount;
    this.source = source;
  }

  public static Transaction createSpend(LocalDateTime localDateTime, int amount, String source) {
    return new Transaction(localDateTime, SPEND, amount, source);
  }

  public static Transaction createDeposit(LocalDateTime localDateTime, int amount, String source) {
    return new Transaction(localDateTime, DEPOSIT, amount, source);
  }

  public static Transaction createInterestCredit(LocalDateTime localDateTime, int amount) {
    return new Transaction(localDateTime, INTEREST, amount, "Interest Credit");
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

  public int signedAmount() {
    return action.equals(SPEND) ? -amount : amount;
  }

  public boolean isInterestCredit() {
    return action.equals(INTEREST);
  }
}
