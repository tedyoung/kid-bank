package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.UserProfile;

import java.time.LocalDateTime;

public class DepositCommand extends ParentTransactionCommand {

  public DepositCommand(Account account, int amount) {
    this(account, amount, "");
  }

  public DepositCommand(Account account, int amount, String description) {
    super(account, amount, description);
  }

  @Override
  protected String response(String formattedAmount, String formattedBalance) {
    return "Deposited " + formattedAmount + ", current balance is now " + formattedBalance;
  }

  @Override
  protected void executeTransaction(UserProfile userProfile) {
    account.deposit(LocalDateTime.now(), amount, description, userProfile);
  }
}
