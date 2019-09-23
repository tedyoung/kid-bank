package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.*;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SmsDepositTest {

  @Test
  public void depositFromParentShouldDepositTheAmount() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();
    String fromPhone = "+16541231234";
    SmsController smsController = new SmsController(account, new FakeUserProfileRepository(
        new UserProfile("name", new PhoneNumber(fromPhone), null, Role.PARENT)
    ));

    TwilioIncomingRequest twilioIncomingRequest = new TwilioIncomingRequest();
    twilioIncomingRequest.setBody("deposit 12.50");
    twilioIncomingRequest.setFrom(fromPhone);

    smsController.incomingSms(twilioIncomingRequest);

    assertThat(account.balance())
        .isEqualTo(12_50);
  }

  @Test
  public void depositFromParentShouldReturnAmountDepositedWithNewBalance() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();
    String fromPhone = "+16541231234";
    SmsController smsController = new SmsController(account, new FakeUserProfileRepository(
        new UserProfile("name", new PhoneNumber(fromPhone), null, Role.PARENT)
    ));

    TwilioIncomingRequest twilioIncomingRequest = new TwilioIncomingRequest();
    twilioIncomingRequest.setBody("deposit 12.50");
    twilioIncomingRequest.setFrom(fromPhone);

    String response = smsController.incomingSms(twilioIncomingRequest);

    assertThat(response)
        .isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response><Message><Body>Deposited $12.50, current balance is now $12.50</Body></Message></Response>");

  }

}
