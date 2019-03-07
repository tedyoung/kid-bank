package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.Account;

public class CommandParser {

  private final Account account;

  public CommandParser(Account account) {
    this.account = account;
  }

  public TransactionCommand parse(String commandText) {
    TransactionCommand command;
    if (commandText.toLowerCase().startsWith("deposit ")) {
      String rawAmount = commandText.split(" ")[1];
      command = new DepositCommand(account, rawAmount);
    } else if (commandText.equalsIgnoreCase("balance")) {
      command = new BalanceCommand(account);
    } else {
      command = new InvalidCommand(commandText);
    }
    return command;
  }
}
