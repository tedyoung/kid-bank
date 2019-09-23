package com.learnwithted.kidbank.domain;

import java.util.List;
import java.util.Optional;

public class DummyUserProfileRepository implements UserProfileRepository {
  @Override
  public Optional<UserProfile> findByPhoneNumber(PhoneNumber phoneNumber) {
    return Optional.empty();
  }

  @Override
  public UserProfile save(UserProfile userProfile) {
    return null;
  }

  @Override
  public Optional<UserProfile> findByEmail(String email) {
    return Optional.empty();
  }

  @Override
  public List<UserProfile> findAll() {
    return null;
  }

  @Override
  public Optional<UserProfile> findById(Long profileId) {
    return Optional.empty();
  }
}
