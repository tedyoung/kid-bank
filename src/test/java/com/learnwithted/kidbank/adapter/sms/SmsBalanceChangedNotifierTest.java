package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.KidBankApplication;
import com.learnwithted.kidbank.TwilioConfig;
import com.learnwithted.kidbank.domain.BalanceChangedNotifier;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SmsBalanceChangedNotifierTest {

  @Test
  public void featureFlagIsFalseShouldReturnNoopNotifer() throws Exception {
    KidBankApplication kidBankApplication = new KidBankApplication();

    BalanceChangedNotifier notifier = kidBankApplication.balanceChangedNotifier(null, false);

    assertThat(notifier)
        .isNotInstanceOf(TwilioSmsBalanceChangedNotifier.class);
  }

  @Test
  public void featureFlagIsTrueShouldReturnRealNotifier() throws Exception {
    KidBankApplication kidBankApplication = new KidBankApplication();

    TwilioConfig twilioConfig = new TwilioConfig();
    twilioConfig.setAccountSid("accountsid");
    twilioConfig.setAuthToken("authtoken");
    twilioConfig.setPhoneNumber("+16505551212");
    BalanceChangedNotifier notifier = kidBankApplication.balanceChangedNotifier(twilioConfig, true);

    assertThat(notifier)
        .isInstanceOf(TwilioSmsBalanceChangedNotifier.class);
  }

}