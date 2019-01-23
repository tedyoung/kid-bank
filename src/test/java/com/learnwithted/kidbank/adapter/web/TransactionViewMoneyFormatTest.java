package com.learnwithted.kidbank.adapter.web;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionViewMoneyFormatTest {

  @Test
  public void format0AsMoneyResultsInDollarSignAnd2Decimals() {
    assertThat(TransactionView.formatAsMoney(0))
        .isEqualTo("$0.00");
  }

  @Test
  public void formatLessThanOneDollarShouldHaveLeadingZero() throws Exception {
    assertThat(TransactionView.formatAsMoney(99))
        .isEqualTo("$0.99");
  }

  @Test
  public void formatMoreThanOneDollarShouldHaveNoLeadingZeroes() throws Exception {
    assertThat(TransactionView.formatAsMoney(123))
        .isEqualTo("$1.23");
  }

  @Test
  public void formatNegativeAmountShouldHaveMinusSignBeforeDollarSign() throws Exception {
    assertThat(TransactionView.formatAsMoney(-895))
        .isEqualTo("-$8.95");
  }
}