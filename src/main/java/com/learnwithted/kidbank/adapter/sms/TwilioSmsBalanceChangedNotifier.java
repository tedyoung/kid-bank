package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.TwilioConfig;
import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.BalanceChangedNotifier;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TwilioSmsBalanceChangedNotifier implements BalanceChangedNotifier {
  private final TwilioConfig twilioConfig;

  public TwilioSmsBalanceChangedNotifier(TwilioConfig twilioConfig) {
    this.twilioConfig = twilioConfig;
    Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
  }

  @Override
  public void balanceChanged(int amount, int balance) {
    PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber());
    PhoneNumber to = new PhoneNumber("+16505551212");
    String body = "Balance changed by "
                      + ScaledDecimals.formatAsMoney(amount)
                      + ", current balance is "
                      + ScaledDecimals.formatAsMoney(balance);
    Message message = Message.creator(to, from, body).create();
    log.info("Twilio message sent: {} with status: ", message.getSid(), message.getStatus());
  }
}
