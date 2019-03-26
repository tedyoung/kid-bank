package com.learnwithted.kidbank.adapter.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.GitProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

  @Autowired
  GitProperties gitProperties;

  @GetMapping("/")
  public String welcome(Model model) {
    model.addAttribute("commitIdShort", gitProperties.getShortCommitId());
    model.addAttribute("commitTime", gitProperties.getCommitTime());
    return "welcome";
  }

}
