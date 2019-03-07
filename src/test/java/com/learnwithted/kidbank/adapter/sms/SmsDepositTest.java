package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;
import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.StubBalanceChangeNotifier;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SmsDepositTest {

  @Test
  public void depositFromParentShouldDepositTheAmount() throws Exception {
    Account account = new Account(new FakeTransactionRepository(), new StubBalanceChangeNotifier());
    SmsController smsController = new SmsController(account, new AlwaysParentPhoneAuthorizer());

    TwilioIncomingRequest twilioIncomingRequest = new TwilioIncomingRequest();
    twilioIncomingRequest.setBody("deposit 12.50");
    twilioIncomingRequest.setFrom("always parent");

    smsController.incomingSms(twilioIncomingRequest);

    assertThat(account.balance())
        .isEqualTo(12_50);
  }

  @Test
  public void depositFromParentShouldReturnAmountDepositedWithNewBalance() throws Exception {
    Account account = new Account(new FakeTransactionRepository(), new StubBalanceChangeNotifier());

    SmsController smsController = new SmsController(account, new AlwaysParentPhoneAuthorizer());

    TwilioIncomingRequest twilioIncomingRequest = new TwilioIncomingRequest();
    twilioIncomingRequest.setBody("deposit 12.50");
    twilioIncomingRequest.setFrom("always parent");

    String response = smsController.incomingSms(twilioIncomingRequest);

    assertThat(response)
        .isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response><Message><Body>Deposited $12.50, current balance is now $12.50</Body></Message></Response>");

  }

}
