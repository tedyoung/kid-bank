package com.learnwithted.kidbank.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PhoneNumberAuthorizer {

  private Map<PhoneNumber, Role> phoneNumberToRole = new HashMap<>();

  public PhoneNumberAuthorizer(@Value("${KNOWN_PHONE_NUMBER}") String knownRawPhoneNumber) {
    PhoneNumber knownPhoneNumber = new PhoneNumber(knownRawPhoneNumber);
    phoneNumberToRole.put(knownPhoneNumber, Role.DEFAULT);
  }

  public boolean isKnown(PhoneNumber rawPhoneNumber) {
    return phoneNumberToRole.containsKey(rawPhoneNumber);
  }

  public void addPhoneWithRole(PhoneNumber phoneNumber, Role role) {
    phoneNumberToRole.put(phoneNumber, role);
  }

  public Role roleFor(PhoneNumber phoneNumber) {
    return phoneNumberToRole.get(phoneNumber);
  }
}
