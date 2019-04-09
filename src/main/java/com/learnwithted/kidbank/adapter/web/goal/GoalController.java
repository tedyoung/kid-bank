package com.learnwithted.kidbank.adapter.web.goal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class GoalController {

  private final GoalService goalService;

  @Autowired
  public GoalController(GoalService goalService) {
    this.goalService = goalService;
  }

  @GetMapping("/goals")
  public String viewGoals(Model model) {
    List<GoalView> goalViews = goalService.findAll();

    model.addAttribute("goals", goalViews);
    model.addAttribute("createGoal", new CreateGoal());

    return "goals";
  }

  @PostMapping("/goals/create")
  public String createNewGoal(@Valid @ModelAttribute("createGoal") CreateGoal createGoal) {
    goalService.create(createGoal);
    return "redirect:/goals";
  }

}
