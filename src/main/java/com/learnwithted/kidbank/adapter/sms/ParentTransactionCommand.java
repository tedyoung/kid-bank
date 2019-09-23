package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.Role;
import com.learnwithted.kidbank.domain.UserProfile;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public abstract class ParentTransactionCommand implements TransactionCommand {
  protected final Account account;
  protected final int amount;
  protected String description = "";

  ParentTransactionCommand(Account account, int amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be greater than 0.");
    }
    this.account = account;
    this.amount = amount;
  }

  ParentTransactionCommand(Account account, int amount, String description) {
    this(account, amount);

    if (!description.isEmpty()) {
      this.description = description;
    }
  }

  public String execute(UserProfile userProfile) {
    if (userProfile.role() != Role.PARENT) {
      return "Only Parents can do that.";
    }

    executeTransaction(userProfile);

    String formattedBalance = ScaledDecimals.formatAsMoney(account.balance());
    String formattedAmount = ScaledDecimals.formatAsMoney(amount);

    return response(formattedAmount, formattedBalance);
  }

  protected abstract String response(String formattedAmount, String formattedBalance);

  protected abstract void executeTransaction(UserProfile userProfile);
}
