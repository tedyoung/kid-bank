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

  private final PhoneNumberAuthorizer phoneNumberAuthorizer;
  private final CommandParser commandParser;

  @Autowired
  public SmsController(Account account, PhoneNumberAuthorizer phoneNumberAuthorizer) {
    this.phoneNumberAuthorizer = phoneNumberAuthorizer;
    commandParser = new CommandParser(account);
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
    TransactionCommand command = commandParser.parse(commandText);
    return wrapInTwiml(command.execute(phoneNumberAuthorizer.roleFor(fromPhone)));
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
