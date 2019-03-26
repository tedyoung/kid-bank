package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.Role;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class DepositCommand implements TransactionCommand {
  private final Account account;
  private final int amountToDeposit;
  private String description = "SMS message";

  DepositCommand(Account account, int amount, String description) {
    this(account, amount);
    this.description = description;
  }

  public String execute(Role role) {
    if (role != Role.PARENT) {
      return "Only Parents can deposit money.";
    }
    account.deposit(LocalDateTime.now(), amountToDeposit, description);

    String formattedBalance = ScaledDecimals.formatAsMoney(account.balance());
    String formattedDeposit = ScaledDecimals.formatAsMoney(amountToDeposit);
    return "Deposited " + formattedDeposit + ", current balance is now " + formattedBalance;
  }
}
