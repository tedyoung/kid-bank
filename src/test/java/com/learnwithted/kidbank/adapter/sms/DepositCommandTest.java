package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.*;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DepositCommandTest {

  private static final UserProfile PARENT_USER_PROFILE = new UserProfile(null, null, null, Role.PARENT);

  @Test
  public void depositCommandShouldDepositAmount() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();
    DepositCommand depositCommand = new DepositCommand(account, 13_75);

    assertThat(depositCommand.execute(PARENT_USER_PROFILE))
        .isEqualTo("Deposited $13.75, current balance is now $13.75");

    assertThat(account.balance())
        .isEqualTo(13_75);
  }

  @Test
  public void depositCommandWithDescriptionCreatesTransactionWithDescription() throws Exception {
    TestAccountBuilder builder = TestAccountBuilder.builder();
    Account account = builder.buildAsCore();
    DepositCommand depositCommand = new DepositCommand(account, 27_95, "Cookie");

    depositCommand.execute(PARENT_USER_PROFILE);

    assertThat(builder.transactionRepository().findAll())
        .hasSize(1)
        .extracting(Transaction::source)
        .containsExactly("Cookie");
  }

  @Test
  public void depositCommandWithAmountLessThanZeroCannotBeCreated() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();
    assertThatThrownBy(() -> new DepositCommand(account, -1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Amount must be greater than 0.");
  }
}
