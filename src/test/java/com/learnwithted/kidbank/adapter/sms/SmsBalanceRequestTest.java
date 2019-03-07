package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;
import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.StubBalanceChangeNotifier;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SmsBalanceRequestTest {

  @Test
  public void fromUnknownNumberShouldReturnEmptyResponse() throws Exception {
    Account account = new Account(new FakeTransactionRepository(), new StubBalanceChangeNotifier());
    SmsController smsController = new SmsController(account, new AlwaysUnknownPhoneAuthorizer());

    TwilioIncomingRequest twilioIncomingRequest = new TwilioIncomingRequest();
    twilioIncomingRequest.setBody("balance");
    twilioIncomingRequest.setFrom("unknown");

    String response = smsController.incomingSms(twilioIncomingRequest);

    assertThat(response)
        .isEmpty();
  }

  @Test
  public void fromKnownAuthorizedNumberShouldReturnBalanceMessage() throws Exception {
    Account account = new Account(new FakeTransactionRepository(), new StubBalanceChangeNotifier());
    SmsController smsController = new SmsController(account, new AlwaysParentPhoneAuthorizer());

    TwilioIncomingRequest twilioIncomingRequest = new TwilioIncomingRequest();
    twilioIncomingRequest.setBody("balance");
    twilioIncomingRequest.setFrom("parent number");

    String response = smsController.incomingSms(twilioIncomingRequest);

    assertThat(response)
        .contains("Your balance is $0.00");
  }

  @Test
  public void fromKnownNumberWithUnknownMessageShouldReturnErrorMessage() throws Exception {
    Account account = new Account(new FakeTransactionRepository(), new StubBalanceChangeNotifier());
    SmsController smsController = new SmsController(account, new AlwaysParentPhoneAuthorizer());

    TwilioIncomingRequest twilioIncomingRequest = new TwilioIncomingRequest();
    twilioIncomingRequest.setBody("wrong message");
    twilioIncomingRequest.setFrom("parent number");

    String response = smsController.incomingSms(twilioIncomingRequest);

    assertThat(response)
        .contains("Did not understand \"wrong message\".");
  }
}