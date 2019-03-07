package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.Role;
import com.learnwithted.kidbank.domain.TestClockSupport;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BalanceCommandTest {

  @Test
  public void balanceCommandShouldReturnFormattedBalanceText() throws Exception {
    Account account = new Account(null, TestClockSupport.createFixedClockOn(2019, 1, 1)) {
      @Override
      public int balance() {
        return 25_00;
      }
    };
    BalanceCommand balanceCommand = new BalanceCommand(account);

    assertThat(balanceCommand.execute(Role.PARENT))
        .isEqualTo("Your balance is $25.00");
  }

}