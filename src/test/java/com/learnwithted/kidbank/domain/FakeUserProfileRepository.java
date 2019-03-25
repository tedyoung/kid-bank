package com.learnwithted.kidbank.domain;

import java.util.Optional;

public class FakeUserProfileRepository implements UserProfileRepository {
  private UserProfile userProfile;

  public FakeUserProfileRepository(UserProfile userProfile) {
    this.userProfile = userProfile;
  }

  @Override
  public Optional<UserProfile> findByPhoneNumber(PhoneNumber phoneNumber) {
    return Optional.ofNullable(userProfile);
  }

  @Override
  public Optional<UserProfile> findByEmail(String email) {
    return Optional.ofNullable(userProfile);
  }

  @Override
  public UserProfile save(UserProfile userProfile) {
    this.userProfile = userProfile;
    return userProfile;
  }
}
