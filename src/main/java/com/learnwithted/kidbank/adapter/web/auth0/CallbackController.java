package com.learnwithted.kidbank.adapter.web.auth0;

import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.Tokens;
import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CallbackController {

  private final String redirectOnFail;
  private final String redirectOnSuccess;
  @Autowired
  private AuthenticationController controller;

  public CallbackController() {
    this.redirectOnFail = "/login";
    this.redirectOnSuccess = "/";
  }

  @GetMapping("/callback")
  protected void getCallback(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
    handle(req, res);
  }

  @PostMapping(value = "/callback", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  protected void postCallback(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
    handle(req, res);
  }

  private void handle(HttpServletRequest req, HttpServletResponse res) throws IOException {
    try {
      Tokens tokens = controller.handle(req);
      TokenAuthentication tokenAuth = new TokenAuthentication(JWT.decode(tokens.getIdToken()));
      SecurityContextHolder.getContext().setAuthentication(tokenAuth);
      res.sendRedirect(redirectOnSuccess);
    } catch (AuthenticationException | IdentityVerificationException e) {
      e.printStackTrace();
      SecurityContextHolder.clearContext();
      res.sendRedirect(redirectOnFail);
    }
  }

}
