package com.learnwithted.kidbank.domain;

import com.learnwithted.kidbank.config.PhoneNumberConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PhoneNumberAuthorizer {

  private final Map<PhoneNumber, Role> phoneNumberToRole = new HashMap<>();

  // for testing purposes
  public PhoneNumberAuthorizer() {
  }

  @Autowired
  public PhoneNumberAuthorizer(PhoneNumberConfig config) {
    phoneNumberToRole.put(new PhoneNumber(config.getParent()), Role.PARENT);
    phoneNumberToRole.put(new PhoneNumber(config.getKid()), Role.KID);
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
