package com.learnwithted.kidbank.domain;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionTest {

  @Test
  public void spendTransactionShouldHaveNegativeTransactionAmount() throws Exception {
    Transaction transaction = Transaction.createSpend(LocalDateTime.now(), 1375, "source");

    assertThat(transaction.signedAmount())
        .isEqualTo(-1375);
  }

}