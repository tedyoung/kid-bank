package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.Action;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ActionFormatterTest {

  @Test
  public void interestCreditActionProperlyCapitalized() throws Exception {
    String actionString = ActionFormatter.format(Action.INTEREST_CREDIT);
    assertThat(actionString)
        .isEqualTo("Interest Credit");
  }

  @Test
  public void singleWordDepositActionOnlyInitialLetterCapitalized() throws Exception {
    String actionString = ActionFormatter.format(Action.DEPOSIT);
    assertThat(actionString)
        .isEqualTo("Deposit");
  }
}