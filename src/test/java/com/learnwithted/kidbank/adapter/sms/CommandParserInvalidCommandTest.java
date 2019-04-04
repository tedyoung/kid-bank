package com.learnwithted.kidbank.adapter.sms;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandParserInvalidCommandTest {

  @Test
  public void invalidCommandTextShouldReturnInvalidCommand() throws Exception {
    CommandParser commandParser = new CommandParser(null);

    TransactionCommand command = commandParser.parse("mispeld");

    assertThat(command)
        .isInstanceOf(InvalidCommand.class);
  }

  @Test
  public void emptyTextShouldBeInvalidCommand() throws Exception {
    CommandParser commandParser = new CommandParser(null);

    TransactionCommand command = commandParser.parse("");

    assertThat(command)
        .isInstanceOf(InvalidCommand.class);
  }

}