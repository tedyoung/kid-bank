package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.TestAccountBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SmsBalanceRequestTest {

  @Test
  public void fromUnknownNumberShouldReturnEmptyResponse() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();
    SmsController smsController = new SmsController(account, new AlwaysUnknownPhoneAuthorizer());

    TwilioIncomingRequest twilioIncomingRequest = new TwilioIncomingRequest();
    twilioIncomingRequest.setBody("balance");
    twilioIncomingRequest.setFrom("+16505550000");

    String response = smsController.incomingSms(twilioIncomingRequest);

    assertThat(response)
        .isEmpty();
  }

  @Test
  public void fromKnownAuthorizedNumberShouldReturnBalanceMessage() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();
    SmsController smsController = new SmsController(account, new AlwaysParentPhoneAuthorizer());

    TwilioIncomingRequest twilioIncomingRequest = new TwilioIncomingRequest();
    twilioIncomingRequest.setBody("balance");
    twilioIncomingRequest.setFrom("+16541231234");

    String response = smsController.incomingSms(twilioIncomingRequest);

    assertThat(response)
        .contains("Your balance is $0.00");
  }

  @Test
  public void fromKnownNumberWithUnknownMessageShouldReturnErrorMessage() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();
    SmsController smsController = new SmsController(account, new AlwaysParentPhoneAuthorizer());

    TwilioIncomingRequest twilioIncomingRequest = new TwilioIncomingRequest();
    twilioIncomingRequest.setBody("wrong message");
    twilioIncomingRequest.setFrom("+16541231234");

    String response = smsController.incomingSms(twilioIncomingRequest);

    assertThat(response)
        .contains("Did not understand \"wrong message\".");
  }
}