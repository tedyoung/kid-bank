package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.CoreAccount;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandParserSpendCommandTest {

  @Test
  public void spendWithSingleWordDescriptionCreatesSpendCommandWithDescription() throws Exception {
    CoreAccount account = null;
    CommandParser commandParser = new CommandParser(account);

    TransactionCommand command = commandParser.parse("spend 37.95 Cards");

    assertThat(command)
        .isEqualTo(new SpendCommand(account, 37_95, "Cards"));
  }

  @Test
  public void spendOnlyWithValidAmountShouldBeSpendCommand() throws Exception {
    CoreAccount account = null;
    CommandParser commandParser = new CommandParser(account);

    TransactionCommand command = commandParser.parse("spend 55");

    SpendCommand expectedSpendCommand = new SpendCommand(account, 55_00);

    assertThat(command)
        .isEqualTo(expectedSpendCommand);
  }

}
