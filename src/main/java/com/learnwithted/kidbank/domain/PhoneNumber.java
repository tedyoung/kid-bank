package com.learnwithted.kidbank.domain;

import lombok.EqualsAndHashCode;

/**
 * U.S. only phone number, stored as E.164.
 * RegEx for validation: ^\+?[1-9]\d{1,14}$
 */
@EqualsAndHashCode
public class PhoneNumber {

  private final String rawPhoneNumber;

  public PhoneNumber(String rawPhoneNumber) {
    if (!isValidUsaNumber(rawPhoneNumber)) {
      throw new IllegalArgumentException("heck no buddy");
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
