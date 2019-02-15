package com.learnwithted.kidbank.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberAuthorizer {

  private final String knownRawPhoneNumber;

  public PhoneNumberAuthorizer(@Value("${KNOWN_PHONE_NUMBER}") String knownRawPhoneNumber) {
    this.knownRawPhoneNumber = knownRawPhoneNumber;
  }

  public boolean isKnown(String rawPhoneNumber) {
    return knownRawPhoneNumber.equals(rawPhoneNumber);
  }
}
