package com.learnwithted.kidbank.adapter.web.auth0;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class LogoutController {

  @GetMapping("/logout")
  protected String logout(final HttpServletRequest req) {
    log.debug("Performing logout and redirecting to " + req.getContextPath() + "/login");
    invalidateSession(req);
    return "redirect:" + req.getContextPath() + "/login";
  }

  private void invalidateSession(HttpServletRequest request) {
    if (request.getSession() != null) {
      request.getSession().invalidate();
    }
  }

}