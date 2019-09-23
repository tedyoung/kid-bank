package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.*;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SmsCreatorTest {

  @Test
  public void parentCommandStoresCreatorInTransaction() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();

    String parentPhoneNumber = "+16541231234";
    UserProfile userProfile = new UserProfile(67L, "A Parent", new PhoneNumber(parentPhoneNumber), "parent@example.com", Role.PARENT);
    FakeUserProfileRepository userProfileRepository = new FakeUserProfileRepository(userProfile);

    SmsController smsController = new SmsController(account, userProfileRepository);

    TwilioIncomingRequest twilioIncomingRequest = new TwilioIncomingRequest();
    twilioIncomingRequest.setBody("deposit 12.50");
    twilioIncomingRequest.setFrom(parentPhoneNumber);

    smsController.incomingSms(twilioIncomingRequest);

    assertThat(account.transactions().iterator().next().creator())
        .contains(userProfile);
  }
}
