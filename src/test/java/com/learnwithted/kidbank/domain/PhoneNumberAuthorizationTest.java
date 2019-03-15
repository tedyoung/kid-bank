package com.learnwithted.kidbank.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PhoneNumberAuthorizationTest {

  @Test
  public void parentPhoneNumberShouldHaveParentRole() throws Exception {
    String rawParentPhoneNumber = "+14155551212";
    PhoneNumber parentPhoneNumber = new PhoneNumber(rawParentPhoneNumber);
    UserProfile parentProfile = new UserProfile("parent", parentPhoneNumber, "email", Role.PARENT);

    PhoneNumberAuthorizer phoneNumberAuthorizer = new PhoneNumberAuthorizer(
        new FakeUserProfileRepository(parentProfile)
    );

    assertThat(phoneNumberAuthorizer.roleFor(parentPhoneNumber))
        .isEqualTo(Role.PARENT);
  }

  @Test
  public void phoneNumberWithRoleShouldBeKnown() throws Exception {
    String rawRolePhoneNumber = "+14155551212";
    PhoneNumber rolePhoneNumber = new PhoneNumber(rawRolePhoneNumber);
    UserProfile kidProfile = new UserProfile("parent", rolePhoneNumber, "email", Role.KID);

    PhoneNumberAuthorizer phoneNumberAuthorizer = new PhoneNumberAuthorizer(
        new FakeUserProfileRepository(kidProfile)
    );

    assertThat(phoneNumberAuthorizer.isKnown(rolePhoneNumber))
        .isTrue();
  }

  @Test
  public void unknownPhoneNumberShouldNotBeKnown() throws Exception {
    PhoneNumberAuthorizer phoneNumberAuthorizer = new PhoneNumberAuthorizer(
        new FakeUserProfileRepository(null)
    );

    PhoneNumber unknownPhoneNumber = new PhoneNumber("+12125551212");
    boolean isKnown = phoneNumberAuthorizer.isKnown(unknownPhoneNumber);

    assertThat(isKnown)
        .isFalse();
  }

}
