package com.learnwithted.kidbank.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class GoogleAuthoritiesExtractor implements AuthoritiesExtractor {

  @Value("${PARENT_EMAIL}")
  private String parentEmail;

  @Override
  public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
    String role = "ROLE_USER"; // default if not parent

    // and check for map.get("hd") which is the domain
    String email = (String) map.get("email");
    if (email.equalsIgnoreCase(parentEmail)) {
      role = "ROLE_PARENT";
    }

    return Collections.singletonList(new SimpleGrantedAuthority(role));
  }

}
