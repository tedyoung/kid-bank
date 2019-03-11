package com.learnwithted.kidbank.adapter.web.auth0;

import com.auth0.AuthenticationController;
import com.learnwithted.kidbank.config.Auth0SecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class LoginController {

  @Autowired
  private AuthenticationController controller;
  @Autowired
  private Auth0SecurityConfig auth0SecurityConfig;

  @GetMapping("/login")
  protected String login(final HttpServletRequest req) {
    String redirectUri = req.getScheme() + "://"
                             + req.getServerName() + ":"
                             + req.getServerPort()
                             + "/callback";
    String authorizeUrl = controller.buildAuthorizeUrl(req, redirectUri)
                                    .withAudience(auth0SecurityConfig.getAudience())
                                    .build();
    log.debug("Performing login, callback will be {}, and authorize URL will be {}",
                 redirectUri, authorizeUrl);
    return "redirect:" + authorizeUrl;
  }

}
