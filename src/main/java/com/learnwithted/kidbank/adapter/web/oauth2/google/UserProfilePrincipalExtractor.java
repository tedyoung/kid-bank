package com.learnwithted.kidbank.adapter.web.oauth2.google;

import com.learnwithted.kidbank.domain.Role;
import com.learnwithted.kidbank.domain.UserProfile;
import com.learnwithted.kidbank.domain.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserProfilePrincipalExtractor implements PrincipalExtractor {

  private final UserProfileRepository userProfileRepository;

  @Autowired
  public UserProfilePrincipalExtractor(UserProfileRepository userProfileRepository) {
    this.userProfileRepository = userProfileRepository;
  }

  @Override
  public Object extractPrincipal(Map<String, Object> map) {
    String email = (String) map.get("email");
    return userProfileRepository.findByEmail(email)
                                .orElse(
                                    new UserProfile("Unknown", null, email, Role.UNKNOWN));
  }
}
