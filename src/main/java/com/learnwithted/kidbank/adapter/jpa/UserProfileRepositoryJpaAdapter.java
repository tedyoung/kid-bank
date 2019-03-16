package com.learnwithted.kidbank.adapter.jpa;

import com.learnwithted.kidbank.domain.PhoneNumber;
import com.learnwithted.kidbank.domain.UserProfile;
import com.learnwithted.kidbank.domain.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserProfileRepositoryJpaAdapter implements UserProfileRepository {

  private final UserProfileJpaRepository userProfileJpaRepository;

  @Autowired
  public UserProfileRepositoryJpaAdapter(UserProfileJpaRepository userProfileJpaRepository) {
    this.userProfileJpaRepository = userProfileJpaRepository;
  }

  @Override
  public Optional<UserProfile> findByPhoneNumber(PhoneNumber phoneNumber) {
    Optional<UserProfileDto> dto = userProfileJpaRepository.findByPhoneNumber(phoneNumber.asRaw());
    return dto.map(UserProfileDto::asUserProfile);
  }

  @Override
  public UserProfile save(UserProfile userProfile) {
    UserProfileDto dto = UserProfileDto.from(userProfile);

    dto = userProfileJpaRepository.save(dto);

    return dto.asUserProfile();
  }
}
