package com.learnwithted.kidbank.adapter.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

interface TransactionDtoJpaRepository extends JpaRepository<TransactionDto, Long> {
}
