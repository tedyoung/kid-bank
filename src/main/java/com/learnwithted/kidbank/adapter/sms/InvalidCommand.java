package com.learnwithted.kidbank.adapter.sms;

public class InvalidCommand implements TransactionCommand {
  private final String originalText;

  public InvalidCommand(String originalText) {
    this.originalText = originalText;
  }

  @Override
  public String execute() {
    return "Did not understand \"" + originalText + "\".";
  }
}
