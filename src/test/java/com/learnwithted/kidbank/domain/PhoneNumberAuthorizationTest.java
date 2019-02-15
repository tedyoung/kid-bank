package com.learnwithted.kidbank.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PhoneNumberAuthorizationTest {

  @Test
  public void knownPhoneNumberShouldBeKnown() throws Exception {
    String phoneNumber = "+12125551212";

    boolean isKnown = new PhoneNumberAuthorizer("+12125551212").isKnown(phoneNumber);

    assertThat(isKnown)
        .isTrue();
  }

  @Test
  public void unknownPhoneNumberShouldNotBeKnown() throws Exception {
    String phoneNumber = "+12125551212";

    boolean isKnown = new PhoneNumberAuthorizer("+11234567890").isKnown(phoneNumber);

    assertThat(isKnown)
        .isFalse();
  }
}
