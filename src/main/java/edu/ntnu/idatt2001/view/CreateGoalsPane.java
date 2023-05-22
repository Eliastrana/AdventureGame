package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.controller.FileDashboard;
import edu.ntnu.idatt2001.controller.SceneSwitcher;
import edu.ntnu.idatt2001.utility.AlertUtil;
import edu.ntnu.idatt2001.utility.SoundPlayer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Frontend Class for creating goals.
 */
public class CreateGoalsPane extends StackPane {

  TextField saveNameField = new TextField();
  TextArea currentGoalsArea = new TextArea();

  ComboBox<String> selectGoalsCategory = new ComboBox<>();
  TextField inputField = new TextField();

  Button updateButton = new Button("Update");

  String clickSound = "src/main/resources/sounds/click.wav";

  String pathToSavedGoals = "src/main/resources/savedGoals/";

  /**
   * Constructor for Pane4.
   */
  public CreateGoalsPane() {

    setStyle("-fx-background-image: url('mainmenubackgroundsmall.jpeg')");


    selectGoalsCategory.setId("comboBox");

    inputField.setId("textField");
    inputField.setPromptText("Enter goal value");

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

    Image backIcon = new Image(Objects.requireNonNull(getClass()
            .getResourceAsStream("/iconography/Back.png")));

    ImageView imageViewBack = new ImageView(backIcon);
    imageViewBack.setPreserveRatio(true);
    imageViewBack.setFitWidth(20); // Set the maximum width for the icon
    imageViewBack.setFitHeight(20); // Set the maximum height for the icon

    backButton.setGraphic(imageViewBack);
    backButton.setOnAction(e -> {
      SoundPlayer.play(clickSound);
      SceneSwitcher.switchToMainMenu();
    });

    HBox goals = new HBox();
    goals.setSpacing(20);
    currentGoalsArea.setPrefSize(200, 200);
    currentGoalsArea.setEditable(false);
    currentGoalsArea.setText(pathToSavedGoals + saveNameField.getText() + ".txt");

    saveNameField.textProperty().addListener((observable, oldValue, newValue) -> {
      String filePath = pathToSavedGoals + newValue + ".txt";
      try {
        String fileContent = Files.readString(Path.of(filePath));
        currentGoalsArea.setText(fileContent.isEmpty() ? "" : fileContent);
      } catch (IOException e) {
        currentGoalsArea.setText(""); // Clear the TextArea if the file cannot be read
        AlertUtil.showAlertBoxError("Error", "Could not read file", e.getMessage());
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
        String filePath = pathToSavedGoals + saveNameField.getText() + ".txt";
        try {
          String fileContent = Files.readString(Path.of(filePath));
          currentGoalsArea.setText(fileContent);
        } catch (IOException ex) {
          currentGoalsArea.setText(""); // Clear the TextArea if the file cannot be read
          AlertUtil.showAlertBoxError("Error", "Could not read file", ex.getMessage());
        }
      }
      inputField.clear();
      selectGoalsCategory.getItems().clear();
      selectGoalsCategory.getItems().addAll("HealthGoal", "ScoreGoal", "GoldGoal", "InventoryGoal");
      selectGoalsCategory.setPromptText("Select category");
      SoundPlayer.play(clickSound);
    });

    updateGoals.getChildren().addAll(updateGoalsTitle,
            saveNameField, selectGoalsCategory, inputField,
            updateButton);
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
        String saveLocation = pathToSavedGoals + saveName;
        FileDashboard.writeGoals(selectedItem + ": " + prefix, saveLocation);
      }
    } catch (IOException ex) {
      AlertUtil.showAlertBoxError("Error", "Could not save file", ex.getMessage());
    }
    SoundPlayer.play(clickSound);
    comboBox.getSelectionModel().clearSelection();
  }

}
