package com.learnwithted.kidbank.domain;

import java.util.List;

// PORT in Hexagonal Architecture
public interface TransactionRepository {

  List<Transaction> findAll();

  Transaction save(Transaction transaction);

  List<Transaction> saveAll(List<Transaction> transactions);

}
