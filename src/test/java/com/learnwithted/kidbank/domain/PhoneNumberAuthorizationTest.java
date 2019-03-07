package com.learnwithted.kidbank.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PhoneNumberAuthorizationTest {

  @Test
  public void parentPhoneNumberShouldBeRecognizedAsParentRole() throws Exception {
    String parentRawPhoneNumber = "+14155551212";
    PhoneNumber parentPhoneNumber = new PhoneNumber(parentRawPhoneNumber);

    PhoneNumberAuthorizer phoneNumberAuthorizer = new PhoneNumberAuthorizer("");
    phoneNumberAuthorizer.addPhoneWithRole(parentPhoneNumber, "parent");

    assertThat(phoneNumberAuthorizer.roleFor(parentPhoneNumber))
        .isEqualTo("parent");
  }

  @Test
  public void phoneNumberWithRoleShouldBeKnown() throws Exception {
    PhoneNumber rolePhoneNumber = new PhoneNumber("+14155551212");

    PhoneNumberAuthorizer phoneNumberAuthorizer = new PhoneNumberAuthorizer("");
    phoneNumberAuthorizer.addPhoneWithRole(rolePhoneNumber, "parent");

    assertThat(phoneNumberAuthorizer.isKnown(rolePhoneNumber))
        .isTrue();
  }

  @Test
  public void knownPhoneNumberShouldBeKnown() throws Exception {
    PhoneNumber phoneNumber = new PhoneNumber("+12125551212");

    boolean isKnown = new PhoneNumberAuthorizer("+12125551212").isKnown(phoneNumber);

    assertThat(isKnown)
        .isTrue();
  }

  @Test
  public void unknownPhoneNumberShouldNotBeKnown() throws Exception {
    PhoneNumber phoneNumber = new PhoneNumber("+12125551212");

    boolean isKnown = new PhoneNumberAuthorizer("+11234567890").isKnown(phoneNumber);

    assertThat(isKnown)
        .isFalse();
  }
}
