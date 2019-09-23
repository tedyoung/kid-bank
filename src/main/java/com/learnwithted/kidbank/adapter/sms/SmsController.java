package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.PhoneNumber;
import com.learnwithted.kidbank.domain.UserProfile;
import com.learnwithted.kidbank.domain.UserProfileRepository;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
@Slf4j
public class SmsController {

  private final CommandParser commandParser;
  private final UserProfileRepository userProfileRepository;

  @Autowired
  public SmsController(Account account,
                       UserProfileRepository userProfileRepository) {
    commandParser = new CommandParser(account);
    this.userProfileRepository = userProfileRepository;
  }

  @PostMapping(
      path = "/sms",
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
      produces = MediaType.APPLICATION_XML_VALUE
  )
  public String incomingSms(TwilioIncomingRequest request) {
    PhoneNumber fromPhone = new PhoneNumber(request.getFrom());
    String commandText = request.getBody();

    UserProfile userProfile = userProfileRepository.findByPhoneNumber(fromPhone)
        .orElseThrow(() -> new NoSuchElementException(
            "Could not find authorized User Profile for " + fromPhone + ", command text was: " + commandText));

    return executeCommand(commandText, userProfile);
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.OK)
  public String handleException(Exception exception) {
    log.info("Authorized Phone Number not found", exception);
    return "";
  }

  private String executeCommand(String commandText, UserProfile userProfile) {
    TransactionCommand command = commandParser.parse(commandText);
    return wrapInTwiml(command.execute(userProfile));
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
