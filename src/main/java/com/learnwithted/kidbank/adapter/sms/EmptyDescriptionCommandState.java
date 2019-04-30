package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.Account;

public class EmptyDescriptionCommandState implements CommandState {
  @Override
  public void execute(Account account, int amount, String description) {
  }

  @Override
  public String response(String formattedAmount, String formattedBalance, String description) {
    return String.format("What did you spend %s on?", formattedAmount);
  }
}
