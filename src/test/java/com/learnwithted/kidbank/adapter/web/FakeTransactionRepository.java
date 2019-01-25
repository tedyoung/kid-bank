package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.Transaction;
import com.learnwithted.kidbank.domain.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

public class FakeTransactionRepository implements TransactionRepository {

  private List<Transaction> transactions = new ArrayList<>();

  @Override
  public List<Transaction> findAll() {
    return transactions;
  }

  @Override
  public Transaction save(Transaction transaction) {
    transactions.add(transaction);
    return transaction;
  }

  @Override
  public List<Transaction> saveAll(List<Transaction> transactionsToSave) {
    transactions.addAll(transactionsToSave);
    return transactionsToSave;
  }
}
