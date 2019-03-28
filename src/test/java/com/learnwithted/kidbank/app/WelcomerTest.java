package com.learnwithted.kidbank.app;

import com.learnwithted.kidbank.domain.*;
import org.junit.Test;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class WelcomerTest {

  @Test
  public void welcomeUserSendsTextMessageToUserPhoneNumber() throws Exception {
    String toPhoneNumber = "+16509871234";
    UserProfile userProfile = new UserProfile("Ted", new PhoneNumber(toPhoneNumber), "", Role.PARENT);
    UserProfileRepository fakeRepo = new FakeUserProfileRepository(userProfile);

    TextMessageSender spy = spy(TextMessageSender.class);
    WelcomerService welcomerService = new WelcomerService(spy, fakeRepo);

    welcomerService.welcome(1L);

    String welcome = "Hi Ted, welcome to Kid Money Manager. You can use the commands BALANCE, DEPOSIT, and SPEND.";
    verify(spy).send(toPhoneNumber, welcome);
  }

}