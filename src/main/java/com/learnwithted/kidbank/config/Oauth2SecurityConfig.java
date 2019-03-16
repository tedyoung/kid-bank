package com.learnwithted.kidbank.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
public class Oauth2SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/", "/error", "/login", "/api/sms")
          .permitAll()
        .antMatchers("/deposit", "/spend", "/import")
          .hasRole("PARENT")
        .anyRequest()
          .authenticated()
        .and()
          .logout()
            .logoutSuccessUrl("/");
  }
}