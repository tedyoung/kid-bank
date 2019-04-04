package com.learnwithted.kidbank.adapter.sms;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandParserBalanceCommandTest {

  @Test
  public void balanceShouldBeParsedAsBalanceCommand() throws Exception {
    CommandParser commandParser = new CommandParser(null);

    TransactionCommand balanceCommand = commandParser.parse("balance");

    assertThat(balanceCommand)
        .isInstanceOf(BalanceCommand.class);
  }


}
