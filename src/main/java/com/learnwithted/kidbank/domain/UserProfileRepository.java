package com.learnwithted.kidbank.domain;

import java.util.Optional;

public interface UserProfileRepository {

  Optional<UserProfile> findByPhoneNumber(PhoneNumber phoneNumber);

  UserProfile save(UserProfile userProfile);
}
