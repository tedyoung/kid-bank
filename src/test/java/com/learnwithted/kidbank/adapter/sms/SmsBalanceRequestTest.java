package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;
import com.learnwithted.kidbank.domain.CoreAccount;
import com.learnwithted.kidbank.domain.StubBalanceChangeNotifier;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SmsBalanceRequestTest {

  @Test
  public void fromUnknownNumberShouldReturnEmptyResponse() throws Exception {
    CoreAccount account = new CoreAccount(new FakeTransactionRepository(), new StubBalanceChangeNotifier());
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
    CoreAccount account = new CoreAccount(new FakeTransactionRepository(), new StubBalanceChangeNotifier());
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
    CoreAccount account = new CoreAccount(new FakeTransactionRepository(), new StubBalanceChangeNotifier());
    SmsController smsController = new SmsController(account, new AlwaysParentPhoneAuthorizer());

    TwilioIncomingRequest twilioIncomingRequest = new TwilioIncomingRequest();
    twilioIncomingRequest.setBody("wrong message");
    twilioIncomingRequest.setFrom("+16541231234");

    String response = smsController.incomingSms(twilioIncomingRequest);

    assertThat(response)
        .contains("Did not understand \"wrong message\".");
  }
}