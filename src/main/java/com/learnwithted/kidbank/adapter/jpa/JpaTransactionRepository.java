package com.learnwithted.kidbank.adapter.jpa;

import com.learnwithted.kidbank.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTransactionRepository extends JpaRepository<Transaction, Long> {
}
