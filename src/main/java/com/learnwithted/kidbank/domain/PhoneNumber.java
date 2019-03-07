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
    this.rawPhoneNumber = rawPhoneNumber;
  }
}
