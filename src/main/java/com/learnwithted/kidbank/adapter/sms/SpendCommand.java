package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.Account;
import lombok.ToString;

@ToString
public class SpendCommand extends ParentTransactionCommand implements TransactionCommand {

  private CommandState commandState;

  public SpendCommand(Account account, int amount) {
    this(account, amount, "");
  }

  public SpendCommand(Account account, int amount, String description) {
    super(account, amount, description);
    updateState();
  }

  @Override
  protected void execute() {
    commandState.execute(account, amount, description);
  }

  @Override
  protected String response(String formattedAmount, String formattedBalance) {
    return commandState.response(formattedAmount, formattedBalance, description);
  }

  public void changeDescriptionTo(String description) {
    super.description = description;
    updateState();
  }

  private void updateState() {
    if (description.isEmpty()) {
      commandState = new EmptyDescriptionCommandState();
    } else {
      commandState = new ExecutableCommandState();
    }
  }
}
