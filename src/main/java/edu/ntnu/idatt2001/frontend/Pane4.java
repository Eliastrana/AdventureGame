package edu.ntnu.idatt2001.frontend;

import edu.ntnu.idatt2001.fileHandling.FileDashboard;
import edu.ntnu.idatt2001.utility.SoundPlayer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

  ComboBox selectGoalsCategory = new ComboBox();
  TextField inputField = new TextField();

  Button updateButton = new Button("Update");


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

    updateButton.setId("confirmGoals");


    selectGoalsCategory.getItems().addAll("HealthGoal", "ScoreGoal", "GoldGoal", "InventoryGoal");
    selectGoalsCategory.setPromptText("Select category");

    updateButton.setOnAction(e -> {
      if (!inputField.getText().isEmpty()) {
        saveAndClearField(selectGoalsCategory, inputField.getText());

        // Update the TextArea with the latest file content
        String filePath = "src/main/resources/savedGoals/" + saveNameField.getText() + ".txt";
        try {
          String fileContent = Files.readString(Path.of(filePath));
          currentGoalsArea.setText(fileContent);
        } catch (IOException ex) {
          currentGoalsArea.setText(""); // Clear the TextArea if the file cannot be read
        }
      }
      inputField.clear();
      selectGoalsCategory.getItems().clear();
      selectGoalsCategory.getItems().addAll("HealthGoal", "ScoreGoal", "GoldGoal", "InventoryGoal");
      selectGoalsCategory.setPromptText("Select category");
      SoundPlayer.play("src/main/resources/sounds/click.wav");
    });




    updateGoals.getChildren().addAll(updateGoalsTitle, saveNameField, selectGoalsCategory,inputField, updateButton);
    goals.getChildren().addAll(currentGoals, updateGoals);
    goals.setAlignment(Pos.CENTER);
    goalsPane.getChildren().addAll(goals);
    backButtonHolder.getChildren().add(backButton);
    structure.getChildren().addAll(backButtonHolder, title, goalsPane);
    structure.setAlignment(Pos.CENTER);
    getChildren().addAll(structure);
  }

  private void saveAndClearField(ComboBox<String> comboBox, String prefix) {
    try {
      String selectedItem = comboBox.getSelectionModel().getSelectedItem();
      if (selectedItem != null) {
        String saveName = saveNameField.getText() + ".txt";
        String saveLocation = "src/main/resources/savedGoals/" + saveName;
        FileDashboard.writeGoals(selectedItem +": "+ prefix, saveLocation);
      }
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }

    SoundPlayer.play("src/main/resources/sounds/click.wav");
    comboBox.getSelectionModel().clearSelection();
  }

}
