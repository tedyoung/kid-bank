package com.learnwithted.kidbank.app;

public interface TextMessageSender {
  void send(String toRawPhoneNumber, String body);
}
