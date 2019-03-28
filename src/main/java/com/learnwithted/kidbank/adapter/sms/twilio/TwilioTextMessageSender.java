package com.learnwithted.kidbank.adapter.sms.twilio;

import com.learnwithted.kidbank.app.TextMessageSender;
import com.learnwithted.kidbank.config.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TwilioTextMessageSender implements TextMessageSender {
  private final PhoneNumber twilioFromPhoneNumber;

  @Autowired
  public TwilioTextMessageSender(TwilioConfig twilioConfig) {
    Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    twilioFromPhoneNumber = new PhoneNumber(twilioConfig.getPhoneNumber());
  }

  @Override
  public void send(String toRawPhoneNumber, String body) {
    PhoneNumber to = new PhoneNumber(toRawPhoneNumber);
    Message message = Message.creator(to, twilioFromPhoneNumber, body).create();
    log.info("Twilio message sent: {} with status: ", message.getSid(), message.getStatus());
  }
}
