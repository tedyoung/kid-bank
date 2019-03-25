package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.*;
import org.junit.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GoogleAuthoritiesExtractorTest {

  @Test
  public void userInfoMapWithParentEmailIsGivenParentRole() throws Exception {
    UserProfile parentProfile = new UserProfile("The Parent", new PhoneNumber("+15555555555"),
                                                "parent@example.com", Role.PARENT);
    UserProfileRepository userProfileRepository = new FakeUserProfileRepository(parentProfile);

    GoogleAuthoritiesExtractor extractor = new GoogleAuthoritiesExtractor(userProfileRepository);
    Map<String, Object> map = new HashMap<>();
    map.put("email", "parent@example.com");

    assertThat(extractor.extractAuthorities(map))
        .hasSize(1)
        .containsExactly(new SimpleGrantedAuthority("ROLE_PARENT"));
  }


  @Test
  public void userInfoMapWithUnknownEmailIsGivenUserRole() throws Exception {
    UserProfileRepository userProfileRepository = new FakeUserProfileRepository(null);
    GoogleAuthoritiesExtractor extractor = new GoogleAuthoritiesExtractor(userProfileRepository);
    Map<String, Object> map = new HashMap<>();
    map.put("email", "unknown@example.com");

    assertThat(extractor.extractAuthorities(map))
        .hasSize(1)
        .containsExactly(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Test
  public void userInfoMapWithKidEmailIsGivenKidRole() throws Exception {
    UserProfile kidProfile = new UserProfile("The Kid", new PhoneNumber("+15555553141"),
                                             "kid@example.com", Role.KID);

    UserProfileRepository userProfileRepository = new FakeUserProfileRepository(kidProfile);
    GoogleAuthoritiesExtractor extractor = new GoogleAuthoritiesExtractor(userProfileRepository);
    Map<String, Object> map = new HashMap<>();
    map.put("email", "kid@example.com");

    assertThat(extractor.extractAuthorities(map))
        .hasSize(1)
        .containsExactly(new SimpleGrantedAuthority("ROLE_KID"));
  }

}