package com.learnwithted.kidbank.adapter.jpa;

import com.learnwithted.kidbank.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

interface JpaTransactionRepository extends JpaRepository<Transaction, Long> {
}
