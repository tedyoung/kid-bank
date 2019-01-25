package com.learnwithted.kidbank.domain;

import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class Account {
  private ImmutableSet<Transaction> transactions = ImmutableSet.of();

  private final TransactionRepository transactionRepository;

  @Autowired
  public Account(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
    List<Transaction> transactionsToLoad = transactionRepository.findAll();
    transactions = ImmutableSet.<Transaction>builder().addAll(transactionsToLoad).build();
  }

  public Account() {
    transactionRepository = null;
  }

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
    if (transactionRepository != null) {
      transaction = transactionRepository.save(transaction);
    }
    transactions = ImmutableSet.<Transaction>builder()
                       .addAll(transactions)
                       .add(transaction)
                       .build();
  }

  public ImmutableSet<Transaction> transactions() {
    return transactions;
  }

  public void load(List<Transaction> transactionsToLoad) {
    if (transactionRepository != null) {
      transactionRepository.saveAll(transactionsToLoad);
    }
    transactions = ImmutableSet.<Transaction>builder()
                       .addAll(transactions)
                       .addAll(transactionsToLoad)
                       .build();
  }
}
