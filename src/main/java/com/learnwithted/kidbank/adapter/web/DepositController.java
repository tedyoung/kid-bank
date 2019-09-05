package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
      @Valid @ModelAttribute("depositCommand") TransactionCommand depositDto,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "deposit";
    }

    int depositAmount = depositDto.amountInCents();
    LocalDateTime dateTime = depositDto.getDateAsLocalDateTime();

    account.deposit(dateTime, depositAmount, depositDto.getDescription());

    return "redirect:" + AccountController.ACCOUNT_URL;
  }

}
