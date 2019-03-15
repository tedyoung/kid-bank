package com.learnwithted.kidbank.domain;

import java.util.Optional;

public class FakeUserProfileRepository implements UserProfileRepository {
  private final UserProfile userProfile;

  public FakeUserProfileRepository(UserProfile userProfile) {
    this.userProfile = userProfile;
  }

  @Override
  public Optional<UserProfile> findByPhoneNumber(PhoneNumber phoneNumber) {
    return Optional.ofNullable(userProfile);
  }
}
