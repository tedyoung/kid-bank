package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.DummyUserProfileRepository;
import com.learnwithted.kidbank.domain.FakeUserProfileRepository;
import com.learnwithted.kidbank.domain.PhoneNumber;
import com.learnwithted.kidbank.domain.TestAccountBuilder;
import com.learnwithted.kidbank.domain.UserProfile;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

public class SmsBalanceRequestTest {

  @Test
  public void fromUnknownNumberShouldReturnEmptyResponse() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();
    SmsController smsController = new SmsController(account, new DummyUserProfileRepository());

    TwilioIncomingRequest twilioIncomingRequest = new TwilioIncomingRequest();
    twilioIncomingRequest.setBody("balance");
    twilioIncomingRequest.setFrom("+16505550000");

    assertThatThrownBy(() -> smsController.incomingSms(twilioIncomingRequest))
        .isInstanceOf(NoSuchElementException.class);

  }

  @Test
  public void fromKnownAuthorizedNumberShouldReturnBalanceMessage() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsInterestEarning(2020, 1, 1);
    String fromPhone = "+16541231234";
    SmsController smsController = new SmsController(account, new FakeUserProfileRepository(
        new UserProfile("name", new PhoneNumber(fromPhone), null, null)
    ));

    TwilioIncomingRequest twilioIncomingRequest = new TwilioIncomingRequest();
    twilioIncomingRequest.setBody("balance");
    twilioIncomingRequest.setFrom(fromPhone);

    String response = smsController.incomingSms(twilioIncomingRequest);

    assertThat(response)
        .contains("Your balance is $0.00");
  }

  @Test
  public void fromKnownNumberWithUnknownMessageShouldReturnErrorMessage() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();
    String fromPhone = "+16541231234";
    SmsController smsController = new SmsController(account,
                                                    new FakeUserProfileRepository(
        new UserProfile("name", new PhoneNumber(fromPhone), null, null)
    ));

    TwilioIncomingRequest twilioIncomingRequest = new TwilioIncomingRequest();
    twilioIncomingRequest.setBody("wrong message");
    twilioIncomingRequest.setFrom(fromPhone);

    String response = smsController.incomingSms(twilioIncomingRequest);

    assertThat(response)
        .contains("Did not understand \"wrong message\".");
  }
}