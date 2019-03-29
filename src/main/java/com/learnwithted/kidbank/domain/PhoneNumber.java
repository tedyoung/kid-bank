package com.learnwithted.kidbank.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class PhoneNumber {

  private final String rawPhoneNumber;

  public PhoneNumber(String rawPhoneNumber) {
    if (!isValidUsaNumber(rawPhoneNumber)) {
      throw new IllegalArgumentException("Not a valid USA Phone Number.");
    }
    this.rawPhoneNumber = rawPhoneNumber;
  }

  private boolean isValidUsaNumber(String rawPhoneNumber) {
    return rawPhoneNumber.matches("^\\+[1-9][0-9]{10}$");
  }

  public String asRaw() {
    return rawPhoneNumber;
  }
}
