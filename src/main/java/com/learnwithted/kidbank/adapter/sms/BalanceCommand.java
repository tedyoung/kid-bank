package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Account;

public class BalanceCommand implements TransactionCommand {
  private final Account account;

  public BalanceCommand(Account account) {
    this.account = account;
  }

  @Override
  public String execute() {
    return "Your balance is " + ScaledDecimals.formatAsMoney(account.balance());
  }

}
