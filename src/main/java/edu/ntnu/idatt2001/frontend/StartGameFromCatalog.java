package edu.ntnu.idatt2001.frontend;

import edu.ntnu.idatt2001.GUI.PaneGenerator;
import edu.ntnu.idatt2001.Game;
import edu.ntnu.idatt2001.fileHandling.CreateGame;
import edu.ntnu.idatt2001.fileHandling.FileDashboard;
import java.io.IOException;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import static edu.ntnu.idatt2001.frontend.Pane1.*;
import static edu.ntnu.idatt2001.frontend.SceneSwitcher.primaryStage;

public class StartGameFromCatalog {
  String saveData;
  String pathFile;
  String characterFile;
  String playerStats;
  String goals;
  String characterIcon;

  StartGameFromCatalog(String saveData, String pathFile, String characterFile, String playerStats, String goals, String characterIcon) {
    this.saveData = saveData;
    this.pathFile = pathFile;
    this.characterFile = characterFile;
    this.playerStats = playerStats;
    this.goals = goals;
    this.characterIcon = characterIcon;
  }

  public String getGoals() {
    return goals;
  }


  public void startGameFromCatalogMethod() throws IOException {


    FileDashboard.gameSave(processSelectedImage(), saveData);


    if (comboBoxPath.getItems() != null) {
      if (comboBoxGoals.getValue() == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("No goal selected");
        alert.setContentText("Please select a goal from the dropdown menu");
        alert.showAndWait();
        return;
      }
      CreateGame game = new CreateGame(pathFile, characterFile, goals, characterIcon);
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      FileDashboard.gameSave(playerStats, saveData);
      FileDashboard.gameSave(comboBoxGoals.getValue() + "\n", saveData);

      PaneGenerator gui;
      Game gameCreated = game.gameGenerator(characterFile);

      if (gameCreated.getStory().getBrokenLinks().size() != 0) {
        Alert brokenLinks = new Alert(Alert.AlertType.WARNING);
        brokenLinks.setTitle("Warning");
        brokenLinks.setHeaderText("Broken links");
        brokenLinks.setContentText("Number of broken links: " + gameCreated.getStory().getBrokenLinks().size()
                + "\n" + "The following links are broken: \n" + gameCreated.getStory().getBrokenLinks()
                + "\n The buttons will be disabled.");

        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        brokenLinks.getButtonTypes().add(cancelButton);

        Optional<ButtonType> result = brokenLinks.showAndWait();
        if (result.isPresent() && result.get() == cancelButton) {
          return;
        }
      }
      String characterIcon = game.getCharacterIconPath();

      gui = new PaneGenerator(gameCreated, saveData, characterIcon);

      gui.start(primaryStage);
      primaryStage.setFullScreen(true);
    }
  }
}
