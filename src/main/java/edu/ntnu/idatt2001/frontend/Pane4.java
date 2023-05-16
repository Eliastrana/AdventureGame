package edu.ntnu.idatt2001.frontend;

import edu.ntnu.idatt2001.fileHandling.FileDashboard;
import edu.ntnu.idatt2001.utility.SoundPlayer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class Pane4 extends StackPane {

  TextField saveNameField = new TextField();
  TextArea currentGoalsArea = new TextArea();
  TextField scoreField = new TextField();
  TextField goldField = new TextField();
  TextField healthField = new TextField();
  TextField inventoryField = new TextField();

  public Pane4() {

    setStyle("-fx-background-image: url('mainmenubackgroundsmall.jpeg')");

    VBox structure = new VBox();
    structure.getStylesheets().add("/Style.css");
    structure.setSpacing(20);
    structure.setPadding(new Insets(20, 20, 20, 20));

    Pane goalsPane = new Pane();
    goalsPane.setId("pane");
    goalsPane.setPadding(new Insets(20));
    goalsPane.setPrefSize(600, 500);
    Text title = new Text("Goals");
    title.setId("title");

    HBox backButtonHolder = new HBox();
    Button backButton = new Button("Back");
    backButton.setId("backNavigation");
    backButton.setAlignment(Pos.TOP_LEFT);
    backButtonHolder.setAlignment(Pos.TOP_LEFT);
    backButton.setOnAction(e -> SceneSwitcher.switchToMainMenu());

    HBox goals = new HBox();
    goals.setSpacing(20);
    currentGoalsArea.setPrefSize(200, 200);
    currentGoalsArea.setEditable(false);
    currentGoalsArea.setText("src/main/resources/savedGoals/" + saveNameField.getText() + ".txt");

    saveNameField.textProperty().addListener((observable, oldValue, newValue) -> {
      String filePath = "src/main/resources/savedGoals/" + newValue + ".txt";
      try {
        String fileContent = Files.readString(Path.of(filePath));
        currentGoalsArea.setText(fileContent);
      } catch (IOException e) {
        currentGoalsArea.setText(""); // Clear the TextArea if the file cannot be read
      }
    });

    VBox currentGoals = new VBox();
    currentGoals.setSpacing(10);
    currentGoals.setId("playerInfo");
    Text currentGoalsTitle = new Text("Current Goals ");
    currentGoalsTitle.setId("mediumTitle");
    currentGoals.getChildren().addAll(currentGoalsTitle, currentGoalsArea);

    VBox updateGoals = new VBox();
    updateGoals.setSpacing(10);
    Text updateGoalsTitle = new Text("Update Goals");
    updateGoalsTitle.setId("mediumTitle");
    saveNameField.setPromptText("Enter save name");
    saveNameField.setId("textFieldGoals");
    scoreField.setPromptText("Enter score goal");
    scoreField.setId("textFieldGoals");
    goldField.setPromptText("Enter gold goal");
    goldField.setId("textFieldGoals");
    healthField.setPromptText("Enter health goal");
    healthField.setId("textFieldGoals");
    inventoryField.setPromptText("Enter inventory goal");
    inventoryField.setId("textFieldGoals");

    Button updateButton = new Button("Update");
    updateButton.setId("confirmGoals");


    updateButton.setOnAction(e -> {
      if (!scoreField.getText().isEmpty()) {
        saveAndClearField(scoreField, "ScoreGoal: ");
      }

      if (!goldField.getText().isEmpty()) {
        saveAndClearField(goldField, "GoldGoal: ");
      }

      if (!healthField.getText().isEmpty()) {
        saveAndClearField(healthField, "HealthGoal: ");
      }

      if (!inventoryField.getText().isEmpty()) {
        saveAndClearField(inventoryField, "InventoryGoal: ");
      }

    });

    updateGoals.getChildren().addAll(updateGoalsTitle, saveNameField,
            scoreField, goldField,
            healthField, inventoryField);
    goals.getChildren().addAll(currentGoals, updateGoals, updateButton);
    goals.setAlignment(Pos.CENTER);
    goalsPane.getChildren().addAll(goals);
    backButtonHolder.getChildren().add(backButton);
    structure.getChildren().addAll(backButtonHolder, title, goalsPane);
    structure.setAlignment(Pos.CENTER);
    getChildren().addAll(structure);
  }

  private void saveAndClearField(TextField field, String prefix) {
    try {
      String saveName = saveNameField.getText() + ".txt";
      String saveLocation = "src/main/resources/savedGoals/" + saveName;
      FileDashboard.writeGoals(prefix + field.getText(), saveLocation);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }

    SoundPlayer.play("src/main/resources/sounds/click.wav");
    field.clear();
  }
}
