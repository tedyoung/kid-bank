package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.Role;

import java.time.LocalDateTime;

public class DepositCommand implements TransactionCommand {
  private final Account account;
  private final int amountToDeposit;

  public DepositCommand(Account account, String rawAmountToDeposit) {
    this.account = account;
    this.amountToDeposit = ScaledDecimals.decimalToPennies(rawAmountToDeposit);
  }

  public String execute(Role role) {
    if (role != Role.PARENT) {
      return "Only Parents can deposit money.";
    }
    account.deposit(LocalDateTime.now(), amountToDeposit, "SMS message");

    String formattedBalance = ScaledDecimals.formatAsMoney(account.balance());
    String formattedDeposit = ScaledDecimals.formatAsMoney(amountToDeposit);
    return "Deposited " + formattedDeposit + ", current balance is now " + formattedBalance;
  }
}
