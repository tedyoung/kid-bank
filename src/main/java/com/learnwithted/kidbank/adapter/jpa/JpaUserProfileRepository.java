package com.learnwithted.kidbank.adapter.jpa;

import com.learnwithted.kidbank.domain.PhoneNumber;
import com.learnwithted.kidbank.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface JpaUserProfileRepository extends JpaRepository<UserProfile, Long> {
  Optional<UserProfile> findByPhoneNumber(PhoneNumber phoneNumber);
}
