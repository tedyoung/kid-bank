package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.DummyUserProfile;
import com.learnwithted.kidbank.domain.TestAccountBuilder;
import com.learnwithted.kidbank.domain.TestClockSupport;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GoalsCommandTest {

  @Test
  public void accountWithNoGoalsReturnsNoGoalsMessage() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();

    TransactionCommand goalsCommand = new GoalsCommand(account);

    assertThat(goalsCommand.execute(null))
        .isEqualTo("Sorry, you don't have any goals.");
  }

  @Test
  public void accountWith35BalanceAnd65GoalTargetReturnsDescriptionOfProgress() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();

    account.deposit(TestClockSupport.localDateTimeAtMidnightOf(2019, 3, 9), 35_00, "initial deposit", new DummyUserProfile());
    account.createGoal("\"Nintendo Game: Our World Is Ended\"", 65_39);

    TransactionCommand goalsCommand = new GoalsCommand(account);

    assertThat(goalsCommand.execute(null))
        .isEqualTo("Your goal is \"Nintendo Game: Our World Is Ended\", which is $65.39, and you have $35.00 saved (54% to your goal) and you need $30.39 more to buy it.");
  }

}