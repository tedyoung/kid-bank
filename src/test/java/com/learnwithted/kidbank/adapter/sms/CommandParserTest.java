package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.CoreAccount;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandParserTest {

  @Test
  public void depositTextOnlyWithoutAmountShouldBeInvalidCommand() throws Exception {
    CommandParser commandParser = new CommandParser(null);

    TransactionCommand command = commandParser.parse("deposit");

    assertThat(command)
        .isInstanceOf(InvalidCommand.class);
  }

  @Test
  public void depositWithInvalidAmountShouldBeInvalidCommand() throws Exception {
    CommandParser commandParser = new CommandParser(null);

    TransactionCommand command = commandParser.parse("deposit xyz");

    assertThat(command)
        .isInstanceOf(InvalidCommand.class);
  }

  @Test
  public void depositOnlyWithPositiveValidAmountCreatesDepositCommand() throws Exception {
    CoreAccount account = null;
    CommandParser commandParser = new CommandParser(account);

    TransactionCommand command = commandParser.parse("deposit 15.75");

    assertThat(command)
        .isEqualTo(new DepositCommand(account, 15_75));
  }

  @Test
  public void depositWithNoDescriptionHasDefaultSmsMessageDescription() throws Exception {
    CommandParser commandParser = new CommandParser(null);

    TransactionCommand command = commandParser.parse("deposit 1.2");

    assertThat(command)
        .isEqualTo(new DepositCommand(null, 1_20, "SMS message"));
  }

  @Test
  public void depositWithNegativeAmountThrowsException() throws Exception {
    CommandParser commandParser = new CommandParser(null);

    TransactionCommand command = commandParser.parse("deposit -1");

    assertThat(command)
        .isEqualTo(new InvalidCommand("Amount must be greater than 0."));
  }

  @Test
  public void depositWithSingleWordDescriptionCreatesDepositCommandWithDescription() throws Exception {
    CoreAccount account = null;
    CommandParser commandParser = new CommandParser(account);

    TransactionCommand command = commandParser.parse("deposit 25 Gift");

    assertThat(command)
        .isEqualTo(new DepositCommand(account, 25_00, "Gift"));
  }

  @Test
  public void depositWithMultipleWordDescriptionCreatesDepositCommandWithDescription() throws Exception {
    CoreAccount account = null;
    CommandParser commandParser = new CommandParser(account);

    TransactionCommand command = commandParser.parse("deposit 2.5 Bottle recycling");

    assertThat(command)
        .isEqualTo(new DepositCommand(account, 2_50, "Bottle recycling"));
  }

}