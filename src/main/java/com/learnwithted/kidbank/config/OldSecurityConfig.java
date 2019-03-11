package com.learnwithted.kidbank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class OldSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
          .antMatchers("/kidbank.css").permitAll()
          .antMatchers("/").hasRole("KID")
          .antMatchers("/deposit", "/spend", "/import").hasRole("PARENT")
        .and()
          .formLogin()
            .loginPage("/login")
            .failureUrl("/login-error");
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    auth
        .inMemoryAuthentication()
          .withUser("parent")
            .password(encoder.encode("parent"))
          .roles("PARENT", "KID")
        .and()
          .withUser("kid")
            .password(encoder.encode("kid"))
          .roles("KID");
  }
}