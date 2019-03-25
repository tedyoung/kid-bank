package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.UserProfile;
import com.learnwithted.kidbank.domain.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class GoogleAuthoritiesExtractor implements AuthoritiesExtractor {

  private final UserProfileRepository userProfileRepository;

  @Autowired
  public GoogleAuthoritiesExtractor(UserProfileRepository userProfileRepository) {
    this.userProfileRepository = userProfileRepository;
  }

  @Override
  public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
    GoogleSignInUser googleSignInUser = GoogleSignInUser.from(map);

    String role = userProfileRepository.findByEmail(googleSignInUser.getEmail())
                                       .map(UserProfile::role)
                                       .map(r -> "ROLE_" + r.name())
                                       .orElse("ROLE_USER");

    return Collections.singletonList(new SimpleGrantedAuthority(role));
  }

  private static class GoogleSignInUser {
    private String email;
    private String hostedDomain;

    static GoogleSignInUser from(Map<String, Object> map) {
      GoogleSignInUser user = new GoogleSignInUser();
      user.setEmail((String) map.get("email"));
      user.setHostedDomain((String) map.get("hd"));
      return user;
    }

    String getEmail() {
      return email;
    }

    private void setEmail(String email) {
      this.email = email;
    }

    String getHostedDomain() {
      return hostedDomain;
    }

    private void setHostedDomain(String hostedDomain) {
      this.hostedDomain = hostedDomain;
    }
  }
}
