package com.learnwithted.kidbank.domain;

import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Component
public class Account {
  public static final double INTEREST_RATE_PER_MONTH = (0.025 / 12);
  private ImmutableSet<Transaction> transactions;

  private final TransactionRepository transactionRepository;
  private final Clock clock;

  @Autowired
  public Account(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
    transactions = ImmutableSet.<Transaction>builder()
                       .addAll(transactionRepository.findAll())
                       .build();
    clock = Clock.systemDefaultZone();
  }

  public Account(TransactionRepository transactionRepository, Clock clock) {
    this.transactionRepository = transactionRepository;
    this.clock = clock;
    this.transactions = ImmutableSet.of();
  }

  public int balance() {
    creditInterestAsNeeded();
    return transactions.stream()
                       .mapToInt(Transaction::signedAmount)
                       .sum();
  }

  private void creditInterestAsNeeded() {
    // get month/year dates for retroactive credits
    LocalDateTime mostRecentTransactionDateTime = mostRecentInterestCreditDateTime();
    // loop from recent interest credit date to first of current clock's month
    LocalDateTime lastDateTimeForCredit = firstDayOfNowMonth();

    LocalDateTime creditDateTime = firstDayOfMonthAfter(mostRecentTransactionDateTime);
    while (!creditDateTime.isAfter(lastDateTimeForCredit)) {
      creditInterestFor(creditDateTime);
      creditDateTime = creditDateTime.plusMonths(1);
    }
  }

  private LocalDateTime firstDayOfMonthAfter(LocalDateTime localDateTime) {
    return localDateTime.plusMonths(1).withDayOfMonth(1);
  }

  private void creditInterestFor(LocalDateTime creditDateTime) {
    int currentBalance = transactions.stream()
                                     .mapToInt(Transaction::signedAmount)
                                     .sum();
    int interestCredit = calculateInterest(currentBalance);

    interestCredit(creditDateTime, interestCredit);
  }

  private LocalDateTime mostRecentInterestCreditDateTime() {
    // most recent interest credit date
    Optional<LocalDateTime> mostRecentInterestCreditTransaction
        = transactions.stream()
                      .filter(Transaction::isInterestCredit)
                      .map(Transaction::dateTime)
                      .max(LocalDateTime::compareTo);

    Supplier<LocalDateTime> firstTransactionDateTime =
        () -> transactions.stream()
                          .map(Transaction::dateTime)
                          .min(LocalDateTime::compareTo)
                          .orElseThrow(IndexOutOfBoundsException::new);

    return mostRecentInterestCreditTransaction.orElseGet(firstTransactionDateTime);
  }

  private LocalDateTime firstDayOfNowMonth() {
    return LocalDateTime.now(clock).withDayOfMonth(1);
  }

  public void interestCredit(LocalDateTime localDateTime, int interestAmount) {
    Transaction interestCreditTransaction = Transaction.createInterestCredit(localDateTime, interestAmount);
    addNewTransaction(interestCreditTransaction);
  }

  private boolean shouldCreditInterest() {
    // have we NOT credited interest yet for the current month?
    LocalDateTime now = LocalDateTime.now(clock);
    boolean hasTransactionForCurrentMonth =
        transactions.stream()
                    .filter(Transaction::isInterestCredit)
                    .map(Transaction::dateTime)
                    .anyMatch(date -> date.getMonthValue() == now.getMonthValue()
                                          && date.getYear() == now.getYear());
    return !hasTransactionForCurrentMonth;
  }

  private int calculateInterest(int currentBalance) {
    // Calculates interest credit with rounding up
    return (int) (currentBalance * INTEREST_RATE_PER_MONTH + 0.5);
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
    Transaction savedTransaction = transactionRepository.save(transaction);
    transactions = ImmutableSet.<Transaction>builder()
                       .addAll(transactions)
                       .add(savedTransaction)
                       .build();
  }

  public ImmutableSet<Transaction> transactions() {
    return transactions;
  }

  public void load(List<Transaction> transactionsToLoad) {
    transactionRepository.saveAll(transactionsToLoad);
    transactions = ImmutableSet.<Transaction>builder()
                       .addAll(transactions)
                       .addAll(transactionsToLoad)
                       .build();
  }
}
