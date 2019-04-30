package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.Role;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public abstract class ParentTransactionCommand implements TransactionCommand {
  protected final Account account;
  protected final int amount;
  protected String description = "";

  public ParentTransactionCommand(Account account, int amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be greater than 0.");
    }
    this.account = account;
    this.amount = amount;
  }

  public ParentTransactionCommand(Account account, int amount, String description) {
    this(account, amount);

    if (!description.isEmpty()) {
      this.description = description;
    }
  }

  public String execute(Role role) {
    if (role != Role.PARENT) {
      return "Only Parents can do that.";
    }

    execute();

    String formattedBalance = ScaledDecimals.formatAsMoney(account.balance());
    String formattedAmount = ScaledDecimals.formatAsMoney(amount);

    return response(formattedAmount, formattedBalance);
  }

  protected abstract String response(String formattedAmount, String formattedBalance);

  protected abstract void execute();
}
