package com.learnwithted.kidbank.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// PORT in Hexagonal Architecture
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  List<Transaction> findAll();

  @Override
  <S extends Transaction> S save(S entity);

  @Override
  <S extends Transaction> List<S> saveAll(Iterable<S> entities);
}
