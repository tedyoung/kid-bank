package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.Role;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BalanceCommand implements TransactionCommand {
  private final Account account;

  @Override
  public String execute(Role role) {
    return "Your balance is " + ScaledDecimals.formatAsMoney(account.balance());
  }

}
