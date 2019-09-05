package com.learnwithted.kidbank.adapter.jpa;

import com.learnwithted.kidbank.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "transactions")
class TransactionDto {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private LocalDateTime date;
  private String action;
  private int amount;
  private String source;

  static TransactionDto from(Transaction transaction) {
    return new TransactionDto(transaction.getId(),
                              transaction.dateTime(),
                              transaction.action(),
                              transaction.amount(),
                              transaction.source());
  }

  Transaction asTransaction() {
    return new Transaction(id, date, action, amount, source);
  }
}
