package com.learnwithted.kidbank.domain;

import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class Account {
  private BalanceChangedNotifier balanceChangedNotifier = (amount, balance) -> {
  };
  private ImmutableSet<Transaction> transactions;

  private final TransactionRepository transactionRepository;
  private final Clock clock;
  private final InterestStrategy interestStrategy;

  @Autowired
  public Account(TransactionRepository transactionRepository,
      BalanceChangedNotifier balanceChangedNotifier,
      InterestStrategy interestStrategy) {
    this.transactionRepository = transactionRepository;
    this.transactions = ImmutableSet.<Transaction>builder()
                            .addAll(transactionRepository.findAll())
                            .build();
    this.clock = Clock.systemDefaultZone();
    this.interestStrategy = interestStrategy;
    this.balanceChangedNotifier = balanceChangedNotifier;
  }

  public Account(TransactionRepository transactionRepository, Clock clock) {
    this(transactionRepository, clock, new MonthlyInterestStrategy(clock));
  }

  public Account(TransactionRepository transactionRepository,
      BalanceChangedNotifier balanceChangedNotifier) {
    this(transactionRepository, balanceChangedNotifier,
         new MonthlyInterestStrategy(Clock.systemDefaultZone()));
  }

  public Account(TransactionRepository transactionRepository,
      Clock clock,
      InterestStrategy interestStrategy) {
    this.clock = clock;
    this.transactionRepository = transactionRepository;
    this.interestStrategy = interestStrategy;
    this.transactions = ImmutableSet.of();
  }

  public int balance() {
    interestStrategy.creditInterestAsNeeded(this);
    return internalBalance();
  }

  private int internalBalance() {
    return transactions.stream()
                       .mapToInt(Transaction::signedAmount)
                       .sum();
  }

  public void interestCredit(LocalDateTime localDateTime, int interestAmount) {
    Transaction interestCreditTransaction = Transaction.createInterestCredit(localDateTime, interestAmount);
    addNewTransaction(interestCreditTransaction);
  }

  public void deposit(LocalDateTime transactionDateTime, int amount, String source) {
    Transaction deposit = Transaction.createDeposit(transactionDateTime, amount, source);
    addNewTransaction(deposit);
    balanceChangedNotifier.balanceChanged(amount, internalBalance());
  }

  public void spend(LocalDateTime transactionDateTime, int amount, String description) {
    Transaction spend = Transaction.createSpend(transactionDateTime, amount, description);
    addNewTransaction(spend);
    balanceChangedNotifier.balanceChanged(-amount, internalBalance());
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

  /**
   * Calculates Balance just prior to (exclusive) the given date
   */
  public int balanceUpTo(LocalDateTime localDateTime) {
    return transactions
               .stream()
               .filter(t -> t.dateTime().isBefore(localDateTime))
               .mapToInt(Transaction::signedAmount)
               .sum();
  }
}
