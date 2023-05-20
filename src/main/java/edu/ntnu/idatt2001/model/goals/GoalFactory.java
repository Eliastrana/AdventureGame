package edu.ntnu.idatt2001.model.goals;

public class GoalFactory {

  public Goal createGoal(String input) {
    if (input == null || input.trim().isEmpty()) {
      return null;
    }

    String[] goalParts = input.trim().split(": ");

    if (goalParts.length == 2) {
      String goalType = goalParts[0];
      String goalValue = goalParts[1];

      switch (goalType) {
        case "HealthGoal":
          try {
            int health = Integer.parseInt(goalValue);
            return new HealthGoal(health);
          } catch (NumberFormatException e) {
            System.err.println("Invalid health value for HealthGoal: " + goalValue);
          }
          break;
        case "GoldGoal":
          try {
            int gold = Integer.parseInt(goalValue);
            return new GoldGoal(gold);
          } catch (NumberFormatException e) {
            System.err.println("Invalid gold value for GoldGoal: " + goalValue);
          }
          break;
        case "InventoryGoal":
          try {
            int inventory = Integer.parseInt(goalValue);
            return new InventoryGoal(inventory);
          } catch (NumberFormatException e) {
            System.err.println("Invalid inventory value for InventoryGoal: " + goalValue);
          }
          break;
        case "ScoreGoal":
          try {
            int score = Integer.parseInt(goalValue);
            return new ScoreGoal(score);
          } catch (NumberFormatException e) {
            System.err.println("Invalid score value for ScoreGoal: " + goalValue);
          }
          break;

        default:
          throw new IllegalArgumentException("Invalid goal type: " + goalType);
      }
    }

    return null;
  }
}
