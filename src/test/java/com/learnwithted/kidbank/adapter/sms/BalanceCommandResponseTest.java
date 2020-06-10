package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.DummyUserProfile;
import com.learnwithted.kidbank.domain.TestAccountBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class BalanceCommandResponseTest {

  @Test
  public void givenCoreAccountRespondsWithOnlyBalanceAmount() throws Exception {
    Account account = TestAccountBuilder
        .builder()
        .initialBalanceOf(10_00)
        .buildAsCore();

    BalanceCommand balanceCommand = new BalanceCommand(account);

    assertThat(balanceCommand.execute(new DummyUserProfile()))
        .isEqualTo("Your balance is $10.00, with interest earned of $0.00.");
  }

  @Test
  public void givenInterestBearingAccountRespondWithAccruedInterestAmount() throws Exception {

    Account account = TestAccountBuilder
        .builder()
        // given an account that has $100 deposit with 1 month's of accrued interest
        .initialBalanceOf(100_00, 2020, 5, 30)
        .buildAsInterestEarning(2020, 6, 2);

    BalanceCommand balanceCommand = new BalanceCommand(account);

    assertThat(balanceCommand.execute(new DummyUserProfile()))
        .isEqualTo("Your balance is $100.21, with interest earned of $0.21.");
  }

  // test for when no interest has been accrued, possibly different message

}