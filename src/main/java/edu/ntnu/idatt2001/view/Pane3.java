package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.utility.exceptions.InvalidFormatException;
import edu.ntnu.idatt2001.utility.filehandling.PlayerRegister;
import edu.ntnu.idatt2001.controller.SceneSwitcher;
import edu.ntnu.idatt2001.utility.SoundPlayer;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Pane3 extends StackPane {

  TextField createPlayerName = new TextField();
  TextField setPlayerHealth = new TextField();
  TextField setPlayerGold = new TextField();
  TextField setPlayerScore = new TextField();
  ComboBox<String> playerInventory = new ComboBox<>();

  public Pane3() {

    setStyle("-fx-background-image: url('mainmenubackgroundsmall.jpeg')");

    playerInventory.setId("comboBox");
    VBox structure = new VBox();
    structure.getStylesheets().add("/Style.css");

    String textFieldId = "textField";

    createPlayerName.setId(textFieldId);
    setPlayerHealth.setId(textFieldId);
    setPlayerGold.setId(textFieldId);
    setPlayerScore.setId(textFieldId);

    Text createPlayer = new Text("Create player:");
    createPlayer.setId("title");
    createPlayerName.setPromptText("Enter player name");
    setPlayerHealth.setPromptText("Enter player health");
    setPlayerGold.setPromptText("Enter player gold");
    setPlayerScore.setPromptText("Enter player score");

    playerInventory.setPromptText("Enter player inventory");
    populatePlayerInventory();

    Button createPlayerButton = new Button("Create player");
    createPlayerButton.setId("confirmButton");

    VBox playerCreation = new VBox();
    playerCreation.getChildren().addAll(createPlayer, createPlayerName,
            setPlayerHealth, setPlayerGold,
            setPlayerScore, playerInventory,
            createPlayerButton);
    playerCreation.setSpacing(10);

    createPlayerButton.setId("navigationButton");

    createPlayerButton.setOnAction(e -> {
      try {
        int playerHealth = Integer.parseInt(setPlayerHealth.getText());
        int playerGold = Integer.parseInt(setPlayerGold.getText());
        int playerScore = Integer.parseInt(setPlayerScore.getText());

        if (createPlayerName.getText().isEmpty() || setPlayerHealth.getText().isEmpty()
                || setPlayerGold.getText().isEmpty() || setPlayerScore.getText().isEmpty()) {
          throw new InvalidFormatException("Empty fields");
        } else if (playerHealth < 0 || playerGold < 0 || playerScore < 0) {
          throw new InvalidFormatException("Invalid input");
        } else {
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.getDialogPane().setId("alertBox");
          alert.setTitle("Player created");
          alert.setHeaderText("You have created a player");
          alert.setContentText("You have created a player");
          alert.showAndWait();
        }

        PlayerRegister register = new PlayerRegister();
        String playerStats = createPlayerName.getText()
                + " " + setPlayerHealth.getText()
                + " " + setPlayerGold.getText()
                + " " + setPlayerScore.getText()
                + " " + playerInventory.getValue();
        register.saveTextToFile(playerStats, "src/main/resources/characters/"
                + createPlayerName.getText()
                + ".paths");

        createPlayerName.clear();
        setPlayerHealth.clear();
        setPlayerGold.clear();
        setPlayerScore.clear();
      } catch (NumberFormatException ex) {
        showAlert("Invalid input", "You have entered an invalid input", "Please enter a valid input");
      } catch (Exception ex) {
        showAlert("Empty fields", "You have not filled out all the fields", "Please fill out all the fields");
      }
    });

    Button backButton = new Button("Back");
    backButton.setId("backNavigation");
    backButton.setAlignment(Pos.TOP_LEFT);
    backButton.setOnAction(e ->  {
      SoundPlayer.play("src/main/resources/sounds/click.wav");
      SceneSwitcher.switchToMainMenu();
    });

    playerCreation.setAlignment(Pos.CENTER);
    structure.getChildren().addAll(backButton, playerCreation);
    structure.setSpacing(20);
    getChildren().addAll(structure);
  }

  private void populatePlayerInventory() {
    playerInventory.getItems().addAll("Sword", "Rock", "Stick", "Flashlight");
  }

  private void showAlert(String title, String headerText, String contentText) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.getDialogPane().setId("alertBox");
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(contentText);
    alert.showAndWait();
  }
}
