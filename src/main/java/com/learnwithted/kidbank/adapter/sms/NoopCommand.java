package com.learnwithted.kidbank.adapter.sms;

public class NoopCommand implements TransactionCommand {
  @Override
  public String execute() {
    return "";
  }
}
