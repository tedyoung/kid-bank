package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;
import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.Role;
import com.learnwithted.kidbank.domain.StubBalanceChangeNotifier;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DepositCommandTest {

  @Test
  public void depositCommandShouldDepositAmount() throws Exception {
    Account account = new Account(new FakeTransactionRepository(), new StubBalanceChangeNotifier());
    DepositCommand depositCommand = new DepositCommand(account, 13_75);

    assertThat(depositCommand.execute(Role.PARENT))
        .isEqualTo("Deposited $13.75, current balance is now $13.75");

    assertThat(account.balance())
        .isEqualTo(13_75);
  }
}
