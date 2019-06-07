package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.Role;
import com.learnwithted.kidbank.domain.TestAccountBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandParserConversationalTest {

  @Test
  public void spendWithNoDescriptionFollowedByDescriptionExecutesSpendCommandWithDescription() throws Exception {
    Account account = TestAccountBuilder.builder()
                                        .initialBalanceOf(100_00)
                                        .buildAsCore();
    CommandParser commandParser = new CommandParser(account);
    TransactionCommand command = commandParser.parse("spend 5");
    command.execute(Role.PARENT);

    command = commandParser.parse("M&Ms at the movies");
    command.execute(Role.PARENT);

    assertThat(account.balance())
        .isEqualTo(95_00);

  }

}
