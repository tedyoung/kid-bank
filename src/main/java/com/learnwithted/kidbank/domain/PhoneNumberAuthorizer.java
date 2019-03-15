package com.learnwithted.kidbank.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberAuthorizer {

  private UserProfileRepository userProfileRepository;

  @Autowired
  public PhoneNumberAuthorizer(UserProfileRepository userProfileRepository) {
    this.userProfileRepository = userProfileRepository;
  }

  public boolean isKnown(PhoneNumber phoneNumber) {
    return userProfileRepository.findByPhoneNumber(phoneNumber)
                                .isPresent();
  }

  public Role roleFor(PhoneNumber phoneNumber) {
    return userProfileRepository.findByPhoneNumber(phoneNumber)
                                .map(UserProfile::role)
                                .orElse(Role.UNKNOWN);
  }
}
