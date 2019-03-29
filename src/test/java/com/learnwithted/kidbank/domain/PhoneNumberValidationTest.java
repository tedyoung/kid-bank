package com.learnwithted.kidbank.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PhoneNumberValidationTest {
  @Test
  public void tooShortNumberShouldThrow() throws Exception {
    assertThatThrownBy(() -> { new PhoneNumber("333"); })
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void correctLengthWithoutLeadingUsaCountryCodeThrowsException() throws Exception {
    assertThatThrownBy(() -> { new PhoneNumber("16505551212"); })
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void usaPhoneNumberWithLeadingPlusOneIsValid() throws Exception {
    assertThat(new PhoneNumber("+16505551212"))
        .isNotNull();
  }
}