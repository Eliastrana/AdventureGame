package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.controller.SceneSwitcher;
import edu.ntnu.idatt2001.utility.AlertUtil;
import edu.ntnu.idatt2001.utility.SoundPlayer;
import edu.ntnu.idatt2001.utility.filehandling.PlayerRegister;
import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;



/**
 * Frontend Class for creating a player.
 */
public class CreateCharacterPane extends StackPane {

  TextField createPlayerName = new TextField();
  TextField setPlayerHealth = new TextField();
  TextField setPlayerGold = new TextField();
  TextField setPlayerScore = new TextField();
  ComboBox<String> playerInventory = new ComboBox<>();

  /**
   * Constructor for Pane3.
   */
  public CreateCharacterPane() {

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
    playerInventory.getItems().addAll("Sword", "Rock", "Stick", "Flashlight");

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
          AlertUtil.showAlertBoxInfo("Empty fields",
                  "You have not filled out all the fields",
                  "Please fill out all the fields");
        } else if (playerHealth < 0 || playerGold < 0 || playerScore < 0) {
          AlertUtil.showAlertBoxInfo("Invalid input",
                  "You have entered an invalid input",
                  "The input must be a positive number");
        } else {
          AlertUtil.showAlertBoxInfo("Player created",
                  "Player " + createPlayerName.getText() + " created",
                  "You will find the player in the character menu");
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
      } catch (NumberFormatException exception) {
        AlertUtil.showAlertBoxInfo("Invalid input",
                "You have entered an invalid input",
                "The input must be a positive number");
      }
    });


    Button backButton = new Button("Back");
    backButton.setId("backNavigation");
    backButton.setAlignment(Pos.TOP_LEFT);

    Image backIcon = new Image(Objects.requireNonNull(getClass()
            .getResourceAsStream("/iconography/Back.png")));

    ImageView imageViewBack = new ImageView(backIcon);
    imageViewBack.setPreserveRatio(true);
    imageViewBack.setFitWidth(20); // Set the maximum width for the icon
    imageViewBack.setFitHeight(20); // Set the maximum height for the icon

    backButton.setGraphic(imageViewBack);
    backButton.setOnAction(e -> {
      SoundPlayer.play("src/main/resources/sounds/click.wav");
      SceneSwitcher.switchToMainMenu();

    });
    VBox backButtonBox = new VBox();
    backButtonBox.getChildren().add(backButton);
    backButtonBox.setAlignment(Pos.TOP_LEFT);
    backButtonBox.setSpacing(10);
    backButtonBox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

    playerCreation.setAlignment(Pos.CENTER);
    structure.getChildren().addAll(backButtonBox, playerCreation);
    structure.setSpacing(20);
    getChildren().addAll(structure);
  }
}



