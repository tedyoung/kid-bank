package com.learnwithted.kidbank.domain;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository {

  Optional<UserProfile> findByPhoneNumber(PhoneNumber phoneNumber);

  UserProfile save(UserProfile userProfile);

  Optional<UserProfile> findByEmail(String email);

  List<UserProfile> findAll();

  Optional<UserProfile> findById(Long profileId);
}
