package com.learnwithted.kidbank.adapter.jpa;

import com.learnwithted.kidbank.domain.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class TransactionDtoJpaTest {

  @Autowired
  private TransactionDtoJpaRepository dtoRepository;

  @Autowired
  private TransactionJpaRepository txnRepository;

  @Before
  public void clear() {
    dtoRepository.deleteAll();
  }

  @Test
  public void newDatabaseIsEmpty() throws Exception {
    assertThat(dtoRepository.count())
        .isZero();
    assertThat(txnRepository.count())
        .isZero();
  }

  @Test
  public void oneSavedDtoIsFoundInRepository() throws Exception {
    TransactionDto transactionDto = new TransactionDto(null, LocalDateTime.now(), "Spend", 50_00, "test");
    TransactionDto saved = dtoRepository.save(transactionDto);

    assertThat(dtoRepository.findById(saved.getId()))
        .isPresent();
  }

  @Test
  public void writeTransactionIsReadIntoDto() throws Exception {
    Transaction txn = Transaction.createDeposit(LocalDateTime.now(), 20_00, "test");
    Transaction savedTxn = txnRepository.save(txn);

    assertThat(dtoRepository.findById(savedTxn.getId()))
        .isPresent();
  }

  @Test
  public void updateOfTransactionDtoKeepsExistingId() throws Exception {
    TransactionDto dto = new TransactionDto(null, LocalDateTime.now(), "deposit", 15_25, "test");
    TransactionDto savedDto = dtoRepository.save(dto);

    Long assignedId = savedDto.getId();

    savedDto.setAmount(80_00);

    TransactionDto changedAndSavedDto = dtoRepository.save(savedDto);

    assertThat(changedAndSavedDto.getId())
        .isEqualTo(assignedId);
    assertThat(changedAndSavedDto.getAmount())
        .isEqualTo(80_00);
  }

}
