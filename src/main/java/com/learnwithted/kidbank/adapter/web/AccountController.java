package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Controller
public class AccountController {

  private final Account account;

  @Autowired
  public AccountController(Account account) {
    this.account = account;
  }

  @GetMapping("/")
  public String viewBalance(Model model, final Principal principal) {
    if (principal == null) {
      return "redirect:/logout";
    }

    int balance = account.balance();
    model.addAttribute("balance", ScaledDecimals.formatAsMoney(balance));

    List<TransactionView> transactionViews = account.transactions().stream()
                                                    .sorted(comparing(Transaction::dateTime).reversed())
                                                    .map(TransactionView::from)
                                                    .collect(Collectors.toList());
    model.addAttribute("transactions", transactionViews);

    return "account-balance";
  }
}
