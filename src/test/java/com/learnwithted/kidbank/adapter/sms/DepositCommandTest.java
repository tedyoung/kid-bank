package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.Role;
import com.learnwithted.kidbank.domain.TestAccountBuilder;
import com.learnwithted.kidbank.domain.Transaction;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DepositCommandTest {

  @Test
  public void depositCommandShouldDepositAmount() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();
    DepositCommand depositCommand = new DepositCommand(account, 13_75);

    assertThat(depositCommand.execute(Role.PARENT))
        .isEqualTo("Deposited $13.75, current balance is now $13.75");

    assertThat(account.balance())
        .isEqualTo(13_75);
  }

  @Test
  public void depositCommandWithDescriptionCreatesTransactionWithDescription() throws Exception {
    TestAccountBuilder builder = TestAccountBuilder.builder();
    Account account = builder.buildAsInterestEarning();
    DepositCommand depositCommand = new DepositCommand(account, 27_95, "Cookie");

    depositCommand.execute(Role.PARENT);

    assertThat(builder.transactionRepository().findAll())
        .hasSize(1)
        .extracting(Transaction::source)
        .containsExactly("Cookie");
  }

  @Test
  public void depositCommandWithAmountLessThanZeroCannotBeCreated() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsInterestEarning();
    assertThatThrownBy(() -> new DepositCommand(account, -1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Amount must be greater than 0.");
  }
}
