package com.learnwithted.kidbank.adapter.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileJpaRepository extends JpaRepository<UserProfileDto, Long> {
  Optional<UserProfileDto> findByPhone(String phoneNumber);

  Optional<UserProfileDto> findByEmail(String email);
}
