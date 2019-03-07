package com.learnwithted.kidbank.domain;

import com.learnwithted.kidbank.config.PhoneNumberConfig;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PhoneNumberAuthorizationTest {

  @Test
  public void parentPhoneNumberShouldBeRecognizedAsParentRole() throws Exception {
    String rawParentPhoneNumber = "+14155551212";
    PhoneNumber parentPhoneNumber = new PhoneNumber(rawParentPhoneNumber);

    PhoneNumberAuthorizer phoneNumberAuthorizer = new PhoneNumberAuthorizer(
        new PhoneNumberConfig(rawParentPhoneNumber, "")
    );

    assertThat(phoneNumberAuthorizer.roleFor(parentPhoneNumber))
        .isEqualTo(Role.PARENT);
  }

  @Test
  public void phoneNumberWithRoleShouldBeKnown() throws Exception {
    String rawRolePhoneNumber = "+14155551212";
    PhoneNumber rolePhoneNumber = new PhoneNumber(rawRolePhoneNumber);

    PhoneNumberAuthorizer phoneNumberAuthorizer = new PhoneNumberAuthorizer(
        new PhoneNumberConfig(rawRolePhoneNumber, "")
    );
    phoneNumberAuthorizer.addPhoneWithRole(rolePhoneNumber, Role.PARENT);

    assertThat(phoneNumberAuthorizer.isKnown(rolePhoneNumber))
        .isTrue();
  }

  @Test
  public void unknownPhoneNumberShouldNotBeKnown() throws Exception {
    PhoneNumberAuthorizer phoneNumberAuthorizer = new PhoneNumberAuthorizer(
        new PhoneNumberConfig("parent", "kid")
    );

    PhoneNumber unknownPhoneNumber = new PhoneNumber("+12125551212");
    boolean isKnown = phoneNumberAuthorizer.isKnown(unknownPhoneNumber);

    assertThat(isKnown)
        .isFalse();
  }
}
