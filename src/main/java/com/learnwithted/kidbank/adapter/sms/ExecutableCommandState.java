package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.Account;

import java.time.LocalDateTime;

public class ExecutableCommandState implements CommandState {
  @Override
  public void execute(Account account, int amount, String description) {
    account.spend(LocalDateTime.now(), amount, description);
  }

  @Override
  public String response(String formattedAmount, String formattedBalance, String description) {
    return String.format(
        "Spent %s on %s, current balance is now %s",
        formattedAmount, description, formattedBalance);
  }
}
