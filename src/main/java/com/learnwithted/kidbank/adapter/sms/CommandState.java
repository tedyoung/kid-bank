package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.Account;

public interface CommandState {
  void execute(Account account, int amount, String description);

  String response(String formattedAmount, String formattedBalance, String description);
}
