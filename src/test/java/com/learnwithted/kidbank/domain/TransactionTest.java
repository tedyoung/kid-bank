package com.learnwithted.kidbank.domain;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionTest {

  private final LocalDateTime NOW = LocalDateTime.now();

  @Test
  public void spendTransactionShouldHaveNegativeTransactionAmount() throws Exception {
    UserProfile userProfile = new UserProfile("username", null, null, null);
    Transaction transaction = Transaction.createSpend(NOW, 1375, "source", userProfile);

    assertThat(transaction.signedAmount())
        .isEqualTo(-1375);
  }

  @Test
  public void depositTransactionHasUserProfileOfItsCreator() throws Exception {
    UserProfile userProfile = new UserProfile("username", null, null, null);

    Transaction transaction = Transaction.createDeposit(NOW, 1, "source", userProfile);

    assertThat(transaction.creator())
        .contains(userProfile);
  }

  @Test
  public void spendTransactionHasUserProfileOfItsCreator() throws Exception {
    UserProfile userProfile = new UserProfile("username", null, null, null);

    Transaction transaction = Transaction.createSpend(NOW, 3, "source", userProfile);

    assertThat(transaction.creator())
        .contains(userProfile);
  }
  
}