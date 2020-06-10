package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.UserProfile;

public class BalanceCommand implements TransactionCommand {
  private final Account account;

  public BalanceCommand(Account account) {
    this.account = account;
  }

  @Override
  public String execute(UserProfile userProfile) {
    String balance = ScaledDecimals.formatAsMoney(account.balance());
    String interest = "$0.00";
    return String.format("Your balance is %s, with interest earned of %s.", balance, interest);
  }

}
