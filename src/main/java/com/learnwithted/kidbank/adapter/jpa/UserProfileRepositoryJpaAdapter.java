package com.learnwithted.kidbank.adapter.jpa;

import com.learnwithted.kidbank.domain.PhoneNumber;
import com.learnwithted.kidbank.domain.UserProfile;
import com.learnwithted.kidbank.domain.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserProfileRepositoryJpaAdapter implements UserProfileRepository {

  private final JpaUserProfileRepository jpaUserProfileRepository;

  @Autowired
  public UserProfileRepositoryJpaAdapter(JpaUserProfileRepository jpaUserProfileRepository) {
    this.jpaUserProfileRepository = jpaUserProfileRepository;
  }

  @Override
  public Optional<UserProfile> findByPhoneNumber(PhoneNumber phoneNumber) {
    return jpaUserProfileRepository.findByPhoneNumber(phoneNumber);
  }
}
