package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.PhoneNumber;
import com.learnwithted.kidbank.domain.PhoneNumberAuthorizer;
import com.learnwithted.kidbank.domain.Role;

class AlwaysUnknownPhoneAuthorizer extends PhoneNumberAuthorizer {

  @Override
  public boolean isKnown(PhoneNumber rawPhoneNumber) {
    return false;
  }

  @Override
  public Role roleFor(PhoneNumber phoneNumber) {
    return Role.UNKNOWN;
  }
}
