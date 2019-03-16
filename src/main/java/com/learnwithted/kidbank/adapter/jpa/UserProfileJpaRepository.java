package com.learnwithted.kidbank.adapter.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UserProfileJpaRepository extends JpaRepository<UserProfileDto, Long> {
  Optional<UserProfileDto> findByPhoneNumber(String phoneNumber);
}
