package com.learnwithted.kidbank.adapter.web.auth0;


import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class TokenAuthentication extends AbstractAuthenticationToken {

  private final DecodedJWT jwt;
  private boolean invalidated;

  public TokenAuthentication(DecodedJWT jwt) {
    super(readAuthorities(jwt));
    this.jwt = jwt;
  }

  private static Collection<? extends GrantedAuthority> readAuthorities(DecodedJWT jwt) {
    Claim rolesClaim = jwt.getClaim("https://access.control/roles");
    if (rolesClaim.isNull()) {
      return Collections.emptyList();
    }
    List<GrantedAuthority> authorities = new ArrayList<>();
    String[] scopes = rolesClaim.asArray(String.class);
    for (String s : scopes) {
      SimpleGrantedAuthority a = new SimpleGrantedAuthority(s);
      if (!authorities.contains(a)) {
        authorities.add(a);
      }
    }
    return authorities;
  }

  private boolean hasExpired() {
    return jwt.getExpiresAt().before(new Date());
  }

  @Override
  public String getCredentials() {
    return jwt.getToken();
  }

  @Override
  public Object getPrincipal() {
    return jwt.getSubject();
  }

  @Override
  public boolean isAuthenticated() {
    return !invalidated && !hasExpired();
  }

  @Override
  public void setAuthenticated(boolean authenticated) {
    if (authenticated) {
      throw new IllegalArgumentException("Create a new Authentication object to authenticate");
    }
    invalidated = true;
  }
}