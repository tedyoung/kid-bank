package com.learnwithted.kidbank.adapter.jpa;

import com.learnwithted.kidbank.domain.Goal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "goals")
class GoalDto {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String description;
  private int targetAmount;

  // FACTORY METHOD
  public static GoalDto from(Goal goal) {
    return new GoalDto(goal.getId(), goal.description(), goal.targetAmount());
  }

  Goal asGoal() {
    return new Goal(description, targetAmount, id);
  }

}
