package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.Role;

public class InvalidCommand implements TransactionCommand {
  private final String originalText;

  public InvalidCommand(String originalText) {
    this.originalText = originalText;
  }

  @Override
  public String execute(Role role) {
    return "Did not understand \"" + originalText + "\".";
  }
}
