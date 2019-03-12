package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Account;

public class CommandParser {

  private final Account account;

  public CommandParser(Account account) {
    this.account = account;
  }

  public TransactionCommand parse(String commandText) {
    commandText = commandText.trim().toLowerCase();

    String[] tokens = commandText.split(" ");
    String commandToken = tokens[0];

    try {
      switch (commandToken) {
        case "balance":
          return new BalanceCommand(account);

        case "deposit":
          return new DepositCommand(account, parseAmount(tokens));

        case "spend":
          return new SpendCommand(account, parseAmount(tokens));

        default:
          throw new InvalidCommandException();
      }
    } catch (InvalidCommandException e) {
      return new InvalidCommand(commandText);
    }
  }

  private int parseAmount(String[] parsed) {
    int amount;
    if (parsed.length == 1) {
      throw new InvalidCommandException();
    }
    try {
      amount = ScaledDecimals.decimalToPennies(parsed[1]);
    } catch (NumberFormatException nfe) {
      throw new InvalidCommandException();
    }
    return amount;
  }
}
