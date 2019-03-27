package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.Role;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
public class SpendCommand implements TransactionCommand {
  private final Account account;
  private final int amountToSpend;
  private String description = "SMS message";

  public SpendCommand(Account account, int amountToSpend, String description) {
    this(account, amountToSpend);
    this.description = description;
  }

  @Override
  public String execute(Role role) {
    if (role != Role.PARENT) {
      return "Only Parents can deposit money.";
    }
    account.spend(LocalDateTime.now(), amountToSpend, description);

    String formattedBalance = ScaledDecimals.formatAsMoney(account.balance());
    String formattedSpend = ScaledDecimals.formatAsMoney(amountToSpend);
    return "Spent " + formattedSpend + ", current balance is now " + formattedBalance;
  }

}
