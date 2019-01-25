package com.learnwithted.kidbank.adapter.jpa;

import com.learnwithted.kidbank.domain.Transaction;
import com.learnwithted.kidbank.domain.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepositoryJpaAdapter implements TransactionRepository {

  private final JpaTransactionRepository jpaTransactionRepository;

  @Autowired
  public TransactionRepositoryJpaAdapter(JpaTransactionRepository jpaTransactionRepository) {
    this.jpaTransactionRepository = jpaTransactionRepository;
  }

  @Override
  public List<Transaction> findAll() {
    return jpaTransactionRepository.findAll();
  }

  @Override
  public Transaction save(Transaction transaction) {
    return jpaTransactionRepository.save(transaction);
  }

  @Override
  public List<Transaction> saveAll(List<Transaction> transactions) {
    return jpaTransactionRepository.saveAll(transactions);
  }
}
