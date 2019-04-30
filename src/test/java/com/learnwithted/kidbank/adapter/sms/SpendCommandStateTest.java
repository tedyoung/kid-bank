package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.TestAccountBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SpendCommandStateTest {

  @Test
  public void withoutDescriptionWhenExecutedShouldNotChangeAccountBalance() throws Exception {
    Account account = TestAccountBuilder.builder()
                                        .initialBalanceOf(10_00)
                                        .buildAsCore();
    SpendCommand spendCommand = new SpendCommand(account, 5_00);

    spendCommand.execute();

    assertThat(account.balance())
        .isEqualTo(10_00);
  }

  @Test
  public void withoutDescriptionExecutedThenDescriptionAddedAndExecutedChangesAccountBalance() throws Exception {
    Account account = TestAccountBuilder.builder()
                                        .initialBalanceOf(10_00)
                                        .buildAsCore();
    SpendCommand spendCommand = new SpendCommand(account, 6_00);
    spendCommand.execute();

    spendCommand.changeDescriptionTo("M&Ms at the movies");
    spendCommand.execute();

    assertThat(account.balance())
        .isEqualTo(4_00);
  }

}
