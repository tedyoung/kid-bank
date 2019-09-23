package com.learnwithted.kidbank.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static com.learnwithted.kidbank.domain.Action.*;

@ToString
public class Transaction {

  // EXTRINSIC property used by Repository
  @Getter
  @Setter
  private Long id;

  // INTRINSIC properties of Transaction
  private LocalDateTime date;
  private Action action;
  private int amountInCents;
  private String source;
  private Optional<UserProfile> creator;

  // RESTRICTED: Used only by TransactionDto coming from the database
  public Transaction(Long id, LocalDateTime date, Action action, int amountInCents, String source, UserProfile creator) {
    this.date = date;
    this.action = action;
    this.amountInCents = amountInCents;
    this.source = source;
    this.creator = Optional.ofNullable(creator);
    this.id = id;
  }

  // backwards compatible (and for interest credit)
  public Transaction(LocalDateTime date, Action action, int amountInCents, String source) {
    this.date = date;
    this.action = action;
    this.amountInCents = amountInCents;
    this.source = source;
    this.creator = Optional.empty();
  }

  public Transaction(LocalDateTime date, Action action, int amountInCents, String source, UserProfile creator) {
    this.date = date;
    this.action = action;
    this.amountInCents = amountInCents;
    this.source = source;
    this.creator = Optional.of(creator);
  }

  public static Transaction createDeposit(LocalDateTime localDateTime, int amount, String source, UserProfile creator) {
    return new Transaction(localDateTime, DEPOSIT, amount, source, creator);
  }

  public static Transaction createSpend(LocalDateTime localDateTime, int amount, String source, UserProfile creator) {
    return new Transaction(localDateTime, SPEND, amount, source, creator);
  }

  public static Transaction createInterestCredit(LocalDateTime localDateTime, int amount) {
    return new Transaction(localDateTime, INTEREST_CREDIT, amount, "Interest Credit");
  }

  public LocalDateTime dateTime() {
    return date;
  }

  public static Predicate<Transaction> isInterestCredit() {
    return t -> t.action.equals(INTEREST_CREDIT);
  }

  public int amount() {
    return amountInCents;
  }

  public String source() {
    return source;
  }

  public int signedAmount() {
    return action.equals(SPEND) ? -amountInCents : amountInCents;
  }

  public Action action() {
    return action;
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

  public Optional<UserProfile> creator() {
    return creator;
  }
}
