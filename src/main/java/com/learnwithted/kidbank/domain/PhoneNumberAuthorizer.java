package com.learnwithted.kidbank.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PhoneNumberAuthorizer {

  private Map<PhoneNumber, String> phoneNumberToRole = new HashMap<>();

  public PhoneNumberAuthorizer(@Value("${KNOWN_PHONE_NUMBER}") String knownRawPhoneNumber) {
    PhoneNumber knownPhoneNumber = new PhoneNumber(knownRawPhoneNumber);
    phoneNumberToRole.put(knownPhoneNumber, "default");
  }

  public boolean isKnown(PhoneNumber rawPhoneNumber) {
    return phoneNumberToRole.containsKey(rawPhoneNumber);
  }

  public void addPhoneWithRole(PhoneNumber phoneNumber, String role) {
    phoneNumberToRole.put(phoneNumber, role);
  }

  public String roleFor(PhoneNumber phoneNumber) {
    return phoneNumberToRole.get(phoneNumber);
  }
}
