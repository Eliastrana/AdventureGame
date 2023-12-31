package edu.ntnu.idatt2001.utility.filehandling;

import edu.ntnu.idatt2001.model.goals.Goal;
import edu.ntnu.idatt2001.model.goals.GoalFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;


/**
 * Class for loading goals from file.
 */
public class GoalRegister {
  private List<Goal> goals;
  private GoalFactory goalFactory;

  public GoalRegister() {
    this.goals = new ArrayList<>();
    this.goalFactory = new GoalFactory();
  }

  /**
   * Method for loading goals from file.
   *
   * @param filename path to the file
   */

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
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("File not found");
      alert.setContentText("The file " + filename + " was not found.");
      alert.showAndWait();
    }
  }

  public List<Goal> getGoals() {
    return new ArrayList<>(this.goals);
  }
}
