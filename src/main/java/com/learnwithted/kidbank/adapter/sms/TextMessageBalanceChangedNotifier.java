package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.app.TextMessageSender;
import com.learnwithted.kidbank.domain.BalanceChangedNotifier;
import org.springframework.stereotype.Service;

@Service
public class TextMessageBalanceChangedNotifier implements BalanceChangedNotifier {

  private final TextMessageSender textMessageSender;

  public TextMessageBalanceChangedNotifier(TextMessageSender textMessageSender) {
    this.textMessageSender = textMessageSender;
  }

  @Override
  public void balanceChanged(int amount, int balance) {
    String to = "+16505551212";
    String body = "Balance changed by "
                      + ScaledDecimals.formatAsMoney(amount)
                      + ", current balance is "
                      + ScaledDecimals.formatAsMoney(balance);
    textMessageSender.send(to, body);
  }
}
