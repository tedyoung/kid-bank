package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.Transaction;
import com.learnwithted.kidbank.domain.TransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class FakeTransactionRepository implements TransactionRepository {

  private List<Transaction> transactions = new ArrayList<>();
  private AtomicLong idGenerator = new AtomicLong(0);

  @Override
  public List<Transaction> findAll() {
    return transactions;
  }

  @Override
  public Transaction save(Transaction transaction) {
    if (transaction.getId() == null) {
      transaction.setId(idGenerator.getAndIncrement());
    }
    transactions.add(transaction);
    return transaction;
  }

  @Override
  public List<Transaction> saveAll(List<Transaction> transactionsToSave) {
    return transactionsToSave.stream()
                             .map(this::save)
                             .collect(Collectors.toList());
  }
}
