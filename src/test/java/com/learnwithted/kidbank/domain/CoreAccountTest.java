package com.learnwithted.kidbank.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class CoreAccountTest {

  @Test
  public void throwsUnsupportedOperationExceptionWhenInterestAmountIsQueried() throws Exception {
    CoreAccount coreAccount = TestAccountBuilder.builder().buildAsCore();

    assertThatThrownBy(() -> {coreAccount.interestEarned();})
      .isInstanceOf(UnsupportedOperationException.class)
      .hasMessage("Core Account does not support querying interest earned because it's not intended to be called in production, it's always wrapped bu the InterestEarningAccount decorator.");
  }

}
