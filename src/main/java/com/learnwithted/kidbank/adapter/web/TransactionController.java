package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Account;
import org.springframework.web.bind.annotation.ModelAttribute;

public class TransactionController {
  protected final Account account;

  public TransactionController(Account account) {
    this.account = account;
  }

  @ModelAttribute(name = "balance")
  public String getBalance() {
    int balance = account.balance();
    return ScaledDecimals.formatAsMoney(balance);
  }
}
