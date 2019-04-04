package com.learnwithted.kidbank.adapter.sms;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandParserGoalsCommandTest {

  @Test
  public void goalOnlyTextIsInvalidCommand() throws Exception {
    CommandParser commandParser = new CommandParser(null);

    TransactionCommand goalCommand = commandParser.parse("goal");

    assertThat(goalCommand)
        .isInstanceOf(InvalidCommand.class);
  }

  @Test
  public void goalsTextParsedIntoGoalCommand() throws Exception {
    CommandParser commandParser = new CommandParser(null);

    TransactionCommand goalsCommand = commandParser.parse("goals");

    assertThat(goalsCommand)
        .isInstanceOf(GoalsCommand.class);
  }
}
