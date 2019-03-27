package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.Account;

import java.time.LocalDateTime;

public class DepositCommand extends ParentTransactionCommand {

  public DepositCommand(Account account, int amount) {
    super(account, amount);
  }

  public DepositCommand(Account account, int amount, String description) {
    super(account, amount, description);
  }

  @Override
  protected String response(String formattedAmount, String formattedBalance) {
    return "Deposited " + formattedAmount + ", current balance is now " + formattedBalance;
  }

  @Override
  protected void execute() {
    account.deposit(LocalDateTime.now(), amount, description);
  }
}
