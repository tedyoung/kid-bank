package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.PhoneNumber;
import com.learnwithted.kidbank.domain.PhoneNumberAuthorizer;
import com.learnwithted.kidbank.domain.Role;

class AlwaysParentPhoneAuthorizer extends PhoneNumberAuthorizer {

  public AlwaysParentPhoneAuthorizer() {
    super(null);
  }

  @Override
  public boolean isKnown(PhoneNumber phoneNumber) {
    return true;
  }

  @Override
  public Role roleFor(PhoneNumber phoneNumber) {
    return Role.PARENT;
  }
}
