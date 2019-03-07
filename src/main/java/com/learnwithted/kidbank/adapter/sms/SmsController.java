package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.PhoneNumber;
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
      produces = MediaType.APPLICATION_XML_VALUE
  )
  public String incomingSms(TwilioIncomingRequest request) {
    PhoneNumber fromPhone = new PhoneNumber(request.getFrom());
    String commandText = request.getBody();
    if (!phoneNumberAuthorizer.isKnown(fromPhone)) {
      log.info("Message {} from unknown number {}", commandText, fromPhone);
      return "";
    }

    return executeCommand(commandText, fromPhone);
  }

  private String executeCommand(String commandText, PhoneNumber fromPhone) {
    TransactionCommand command = new NoopCommand();
    if (commandText.toLowerCase().startsWith("deposit ")) {
      if (phoneNumberAuthorizer.roleFor(fromPhone).equalsIgnoreCase("parent")) {
        String rawAmount = commandText.split(" ")[1];
        command = new DepositCommand(account, rawAmount);
      }
    } else if (commandText.equalsIgnoreCase("balance")) {
      command = new BalanceCommand(account);
    } else {
      command = new InvalidCommand(commandText);
    }
    return wrapInTwiml(command.execute());
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
