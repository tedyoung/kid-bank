package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.UserProfile;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
public class SpendCommand extends ParentTransactionCommand {

  public SpendCommand(Account account, int amount) {
    this(account, amount, "");
  }

  public SpendCommand(Account account, int amount, String description) {
    super(account, amount, description);
  }

  @Override
  protected String response(String formattedAmount, String formattedBalance) {
    return "Spent " + formattedAmount + ", current balance is now " + formattedBalance;
  }

  @Override
  protected void executeTransaction(UserProfile userProfile) {
    account.spend(LocalDateTime.now(), amount, description, userProfile);

  }

}
