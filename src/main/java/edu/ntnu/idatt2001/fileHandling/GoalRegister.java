package edu.ntnu.idatt2001.fileHandling;

import edu.ntnu.idatt2001.goals.Goal;
import edu.ntnu.idatt2001.goals.GoalFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoalRegister {
  private List<Goal> goals;
  private GoalFactory goalFactory;

  public GoalRegister() {
    this.goals = new ArrayList<>();
    this.goalFactory = new GoalFactory();
  }

  public void loadGoalsFromFile(String filename) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = reader.readLine()) != null) {
        Goal goal = goalFactory.createGoal(line);
        if (goal != null) {
          goals.add(goal);
        }
      }
    } catch (IOException e) {
      System.out.println("An error occurred while reading the file: " + e.getMessage());
    }
  }

  public List<Goal> getGoals() {
    return new ArrayList<>(this.goals);
  }
}