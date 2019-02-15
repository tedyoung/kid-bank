package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.PhoneNumberAuthorizer;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class SmsController {

  private final Account account;
  private final PhoneNumberAuthorizer phoneNumberAuthorizer;

  @Autowired
  public SmsController(Account account, PhoneNumberAuthorizer phoneNumberAuthorizer) {
    this.account = account;
    this.phoneNumberAuthorizer = phoneNumberAuthorizer;
  }

  @PostMapping(
      path = "/sms",
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
      produces = MediaType.APPLICATION_XML_VALUE)
  public String incomingSms(TwilioIncomingRequest request) {
    String commandText = request.getBody();
    String fromPhone = request.getFrom();
    if (phoneNumberAuthorizer.isKnown(fromPhone)) {
      return executeCommand(commandText);
    }
    log.info("Message {} from unknown number {}", commandText, fromPhone);
    return "";
  }

  private String executeCommand(String commandText) {
    if (!commandText.equalsIgnoreCase("balance")) {
      return wrapInTwiml("Did not understand \"" + commandText + "\", use BALANCE to check your account balance.");
    }

    int balance = account.balance();
    return balanceInTwiml(ScaledDecimals.formatAsMoney(balance));
  }

  private String balanceInTwiml(String balanceAmount) {
    return wrapInTwiml("Your balance is " + balanceAmount);
  }

  private String wrapInTwiml(String message) {
    Body body = new Body.Builder(message)
                    .build();
    Message sms = new Message.Builder()
                      .body(body)
                      .build();
    MessagingResponse twiml = new MessagingResponse.Builder()
                                  .message(sms)
                                  .build();
    return twiml.toXml();
  }

}
