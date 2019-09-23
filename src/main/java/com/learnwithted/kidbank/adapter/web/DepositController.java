package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/deposit")
public class DepositController extends TransactionController {

  @Autowired
  public DepositController(Account account) {
    super(account);
  }

  @GetMapping
  public String depositForm(Model model) {
    TransactionCommand depositCommand = TransactionCommand.createWithTodayDate();
    model.addAttribute("depositCommand", depositCommand);

    return "deposit";
  }

  @PostMapping
  public String processDepositCommand(
      @Valid @ModelAttribute("depositCommand") TransactionCommand depositCommand,
      Errors errors,
      @AuthenticationPrincipal UserProfile userProfile) {
    if (errors.hasErrors()) {
      return "deposit";
    }

    int depositAmount = depositCommand.amountInCents();
    LocalDateTime dateTime = depositCommand.getDateAsLocalDateTime();

    account.deposit(dateTime, depositAmount, depositCommand.getDescription(), userProfile);

    return "redirect:" + AccountController.ACCOUNT_URL;
  }

}
