package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;
import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.CoreAccount;
import com.learnwithted.kidbank.domain.Role;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandRoleTest {

  @Test
  public void parentRoleShouldBeAllowedToExecuteDepositCommand() throws Exception {
    Account account = new CoreAccount(new FakeTransactionRepository()
    );
    DepositCommand depositCommand = new DepositCommand(account, 25_00);

    depositCommand.execute(Role.PARENT);

    assertThat(account.balance())
        .isEqualTo(25_00);
  }

  @Test
  public void kidRoleShouldNotBeAllowedToExecuteDepositCommand() throws Exception {
    Account account = new CoreAccount(new FakeTransactionRepository()
    );
    DepositCommand depositCommand = new DepositCommand(account, 25_00);

    depositCommand.execute(Role.KID);

    assertThat(account.balance())
        .isZero();
  }

}