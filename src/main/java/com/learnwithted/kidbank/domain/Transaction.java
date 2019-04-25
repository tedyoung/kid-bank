package com.learnwithted.kidbank.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Predicate;

@ToString
@NoArgsConstructor
@Entity
@Table(name = "transactions")
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

  public static Transaction createFooBar(LocalDateTime localDateTime, int amount, String source) {
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

  public static Predicate<Transaction> isInterestCredit() {
    return t -> t.action.equals(INTEREST);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Transaction that = (Transaction) o;

    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}
