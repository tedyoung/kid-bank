package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.Role;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@EqualsAndHashCode
@ToString
public class DepositCommand implements TransactionCommand {
  private final Account account;
  private final int amountToDeposit;
  private String description = "SMS message";

  public DepositCommand(Account account, int amountToDeposit) {
    if (amountToDeposit <= 0) {
      throw new IllegalArgumentException("Amount must be greater than 0.");
    }
    this.account = account;
    this.amountToDeposit = amountToDeposit;
  }

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
