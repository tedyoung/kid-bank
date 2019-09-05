package com.learnwithted.kidbank.adapter.jpa;

import com.learnwithted.kidbank.domain.Transaction;
import com.learnwithted.kidbank.domain.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
class TransactionJpaAdapter implements TransactionRepository {

  private final TransactionDtoJpaRepository dtoJpaRepository;

  @Autowired
  TransactionJpaAdapter(TransactionDtoJpaRepository dtoJpaRepository) {
    this.dtoJpaRepository = dtoJpaRepository;
  }

  @Override
  public List<Transaction> findAll() {
    return dtoJpaRepository.findAll()
                           .stream()
                           .map(TransactionDto::asTransaction)
                           .collect(Collectors.toList());
  }

  public Transaction save(Transaction transaction) {
    TransactionDto dto = TransactionDto.from(transaction);

    TransactionDto savedDto = dtoJpaRepository.save(dto);

    return savedDto.asTransaction();
  }

  @Override
  public List<Transaction> saveAll(List<Transaction> transactions) {
    List<TransactionDto> dtos = transactions.stream()
                                            .map(TransactionDto::from)
                                            .collect(Collectors.toList());

    return dtoJpaRepository.saveAll(dtos)
                           .stream()
                           .map(TransactionDto::asTransaction)
                           .collect(Collectors.toList());
  }

}
