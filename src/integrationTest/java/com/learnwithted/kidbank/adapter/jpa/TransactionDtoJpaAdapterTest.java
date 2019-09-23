package com.learnwithted.kidbank.adapter.jpa;

import com.learnwithted.kidbank.domain.Action;
import com.learnwithted.kidbank.domain.Transaction;
import com.learnwithted.kidbank.domain.UserProfile;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class TransactionDtoJpaAdapterTest {

  private static final LocalDateTime NOW = LocalDateTime.now();

  private UserProfile creator;

  @Autowired
  private TransactionDtoJpaRepository dtoJpaRepository;

  @Autowired
  private UserProfileRepositoryJpaAdapter userProfileRepositoryJpaAdapter;

  @Before
  public void clear() {
    dtoJpaRepository.deleteAll();
    creator = userProfileRepositoryJpaAdapter.findAll().get(0);
  }

  @Test
  public void newDatabaseIsEmpty() throws Exception {
    assertThat(dtoJpaRepository.count())
        .isZero();
  }

  @Test
  public void newTransactionAssignedId() throws Exception {
    TransactionJpaAdapter jpaAdapter = new TransactionJpaAdapter(dtoJpaRepository);
    Transaction transaction = Transaction.createSpend(NOW, 3_45, "test", creator);

    Transaction savedTransaction = jpaAdapter.save(transaction);

    assertThat(savedTransaction.getId())
        .isNotNull();
  }

  @Test
  public void existingTransactionUpdatesDatabase() throws Exception {
    TransactionJpaAdapter jpaAdapter = new TransactionJpaAdapter(dtoJpaRepository);
    Transaction transaction = Transaction.createSpend(NOW, 3_45, "test", creator);
    Transaction savedTransaction = jpaAdapter.save(transaction);
    Long assignedId = savedTransaction.getId();

    Transaction depositTxn = Transaction.createDeposit(NOW, 6_22, "test", creator);
    depositTxn.setId(assignedId);

    savedTransaction = jpaAdapter.save(depositTxn);
    assertThat(savedTransaction.getId())
        .isEqualTo(assignedId);
    assertThat(savedTransaction.action())
        .isEqualTo(Action.DEPOSIT);
  }

  @Test
  public void savedTransactionsAreAllRetrievedByFindAll() throws Exception {
    TransactionJpaAdapter jpaAdapter = new TransactionJpaAdapter(dtoJpaRepository);
    jpaAdapter.save(Transaction.createSpend(NOW, 1_23, "test1", creator));
    jpaAdapter.save(Transaction.createDeposit(NOW, 3_45, "test2", creator));

    List<Transaction> all = jpaAdapter.findAll();

    assertThat(all)
        .hasSize(2)
        .extracting(Transaction::signedAmount)
        .containsOnly(3_45, -1_23);
  }

  @Test
  public void saveAllTransactionsCanBeFoundByFindAll() throws Exception {
    TransactionJpaAdapter jpaAdapter = new TransactionJpaAdapter(dtoJpaRepository);
    Transaction deposit = Transaction.createDeposit(NOW, 6_22, "saveAllDeposit", creator);
    Transaction spend = Transaction.createSpend(NOW, 24_56, "saveAllSpend", creator);

    jpaAdapter.saveAll(Lists.list(deposit, spend));

    assertThat(jpaAdapter.findAll())
        .hasSize(2)
        .extracting(Transaction::source)
        .containsOnly("saveAllDeposit", "saveAllSpend");
  }

  @Test
  public void transactionsWithNoCreatorAreSuccessfullyDeserializedFromDto() throws Exception {
    TransactionJpaAdapter jpaAdapter = new TransactionJpaAdapter(dtoJpaRepository);
    Transaction noCreatorTxn = new Transaction(NOW, Action.DEPOSIT, 102_37, "noCreatorTest");

    jpaAdapter.save(noCreatorTxn);

    assertThat(jpaAdapter.findAll().iterator().next().creator())
        .isNotPresent();
  }

}
