package com.learnwithted.kidbank.adapter.jpa;

import com.learnwithted.kidbank.domain.Transaction;
import com.learnwithted.kidbank.domain.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepositoryJpaAdapter implements TransactionRepository {

  private final TransactionJpaRepository transactionJpaRepository;

  @Autowired
  public TransactionRepositoryJpaAdapter(TransactionJpaRepository transactionJpaRepository) {
    this.transactionJpaRepository = transactionJpaRepository;
  }

  @Override
  public List<Transaction> findAll() {
    return transactionJpaRepository.findAll();
  }

  @Override
  public Transaction save(Transaction transaction) {
    return transactionJpaRepository.save(transaction);
  }

  @Override
  public List<Transaction> saveAll(List<Transaction> transactions) {
    return transactionJpaRepository.saveAll(transactions);
  }
}
