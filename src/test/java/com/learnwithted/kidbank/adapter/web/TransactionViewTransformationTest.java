package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.Transaction;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionViewTransformationTest {
  @Test
  public void transactionShouldBeTransformedToView() throws Exception {
    Transaction transaction = new Transaction(LocalDateTime.of(2012, 2, 9, 0, 0), "Deposit", 4598, "Gift");

    TransactionView view = TransactionView.from(transaction);

    assertThat(view)
        .isEqualTo(new TransactionView("02/09/2012", "Deposit", "$45.98", "Gift"));
  }
}
