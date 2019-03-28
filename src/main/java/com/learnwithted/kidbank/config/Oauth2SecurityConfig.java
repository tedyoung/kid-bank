package com.learnwithted.kidbank.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
public class Oauth2SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().mvcMatchers("/api/sms");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/", "/error", "/login")
          .permitAll()
        .antMatchers("/deposit", "/spend", "/import", "/users")
          .hasRole("PARENT")
        .anyRequest()
          .authenticated()
        .and()
          .logout()
            .logoutSuccessUrl("/");
  }
}