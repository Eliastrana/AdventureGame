package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.controller.FileDashboard;
import edu.ntnu.idatt2001.controller.SceneSwitcher;
import edu.ntnu.idatt2001.utility.AlertUtil;
import edu.ntnu.idatt2001.utility.SoundPlayer;
import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;



/**
 * Frontend Class for writing a file.
 */
public class CreateGamePane extends StackPane {

  /**
   * Constructor for Pane2.
   */
  public CreateGamePane() {
    setStyle("-fx-background-image: url('mainmenubackgroundsmall.jpeg')");

    VBox structure = new VBox();
    structure.getStylesheets().add("Style.css");
    structure.setSpacing(10);

    VBox gameCreation = new VBox();
    gameCreation.setAlignment(Pos.CENTER);
    gameCreation.setSpacing(10);
    gameCreation.getStylesheets().add("Style.css");
    TextField writeGameSaveName = new TextField();
    writeGameSaveName.setPromptText("Write game save name");

    String textFieldId = "textField";
    writeGameSaveName.setId(textFieldId);

    TextField writeGameFieldTitle = new TextField();
    writeGameFieldTitle.setPromptText("Write game field title");
    writeGameFieldTitle.setId(textFieldId);

    TextArea writeGameContent = new TextArea();
    writeGameContent.setPromptText("Write game content");
    writeGameContent.setId(textFieldId);

    TextField writeGameButtonText1 = new TextField();
    writeGameButtonText1.setPromptText("Write game button text 1");
    writeGameButtonText1.setId(textFieldId);
    TextField writeGameButtonTarget1 = new TextField();
    writeGameButtonTarget1.setPromptText("Write game button target 1");
    writeGameButtonTarget1.setId(textFieldId);
    HBox gameButton1 = new HBox();
    gameButton1.getChildren().addAll(writeGameButtonText1, writeGameButtonTarget1);
    gameButton1.setAlignment(Pos.CENTER);
    gameButton1.setSpacing(10);

    TextField writeGameButtonText2 = new TextField();
    writeGameButtonText2.setPromptText("Write game button text 2");
    writeGameButtonText2.setId(textFieldId);

    TextField writeGameButtonTarget2 = new TextField();
    writeGameButtonTarget2.setPromptText("Write game button target 2");
    writeGameButtonTarget2.setId(textFieldId);
    HBox gameButton2 = new HBox();
    gameButton2.setAlignment(Pos.CENTER);
    gameButton2.setSpacing(10);

    gameButton2.getChildren().addAll(writeGameButtonText2, writeGameButtonTarget2);

    HBox actionButtonHbox = new HBox();
    actionButtonHbox.setSpacing(10);
    actionButtonHbox.setAlignment(Pos.CENTER);

    ComboBox<String> actionComboBox = new ComboBox<>();
    actionComboBox.getItems().addAll("HealthAction",
            "GoldAction", "ScoreAction", "InventoryAction");
    actionComboBox.setPromptText("Choose action");
    actionComboBox.setId("comboBox");

    TextField writeActionTarget = new TextField();
    writeActionTarget.setPromptText("Write action sum: ");
    writeActionTarget.setId("textField");

    actionButtonHbox.getChildren().addAll(actionComboBox, writeActionTarget);

    Button writeGameButton = new Button("Write game");
    writeGameButton.setAlignment(Pos.CENTER);
    writeGameButton.setId("navigationButton");
    writeGameButton.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
    writeGameButton.setOnAction(e -> {
      SoundPlayer.play("src/main/resources/sounds/click.wav");
      String fileName = writeGameSaveName.getText(); // add .paths extension to filename

      if (fileName.isBlank()) {
        AlertUtil.showAlertBoxInfo("Info", "No filename", "Please write a filename");
      }


      String buttonAndLink = "";

      String buttonAndLink1 = "[" + writeGameButtonText1.getText()
              + "]" + "(" + writeGameButtonTarget1.getText() + ")";
      String buttonAndLink2 = "[" + writeGameButtonText2.getText()
              + "]" + "(" + writeGameButtonTarget2.getText() + ")";


      if (writeGameButtonText1.getText().isBlank() || writeGameButtonTarget1.getText().isBlank()) {
        buttonAndLink1 = "";
      }
      if (writeGameButtonText2.getText().isBlank() || writeGameButtonTarget2.getText().isBlank()) {
        buttonAndLink2 = "";
      }

      buttonAndLink = buttonAndLink1 + "\n" + buttonAndLink2;
      String titleAndText = ":: " + writeGameFieldTitle.getText()
              + "\n" + writeGameContent.getText() + "\n";
      String actions = "{" + actionComboBox.getValue() + ": "
              + writeActionTarget.getText() + "}";
      String gameContent = titleAndText + buttonAndLink + "\n" + actions;


      try {
        FileDashboard.write(gameContent, fileName);
        writeGameSaveName.setText("");
        writeGameFieldTitle.setText("");
        writeGameContent.setText("");
        writeGameButtonText1.setText("");
        writeGameButtonTarget1.setText("");
        writeGameButtonText2.setText("");
        writeGameButtonTarget2.setText("");
        writeActionTarget.setText("");
        actionComboBox.setValue(null);

        AlertUtil.showAlertBoxInfo("Info", "Game created!",
                "You can find your game in the path selector in Load Game");
      } catch (Exception ex) {
        AlertUtil.showAlertBoxError("Error", "Error writing to file", ex.getMessage());
      }
    });

    gameCreation.getChildren().addAll(writeGameSaveName,
            writeGameFieldTitle, writeGameContent, gameButton1,
            gameButton2, actionButtonHbox, writeGameButton);

    VBox createGameVbox = new VBox();
    createGameVbox.getChildren().addAll(gameCreation);


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
      SceneSwitcher.switchToMainMenu();
      SoundPlayer.play("src/main/resources/sounds/click.wav");

    });

    Button helpButton = new Button("?");
    helpButton.setId("backNavigation");
    helpButton.setOnAction(e -> {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Help");
      alert.setHeaderText("How to create a game");
      String textBlock = """
              Write a name for your game save
              , a title for your game field, the content of your game field
              and the text and target of the buttons.
              If you only want one button
              , leave the other button text and target blank.
              When you are done, press the Write game button and your game will be saved in
              the path selector in Load Game.
              As long as the save name is the same, it will save your passage in the same place.
              """;

      alert.setContentText(textBlock);
      alert.showAndWait();
    });

    helpButton.setAlignment(Pos.TOP_RIGHT);

    HBox topBar = new HBox();
    topBar.getChildren().addAll(backButton, helpButton);
    topBar.setSpacing(15);
    topBar.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

    structure.getChildren().addAll(topBar, createGameVbox);

    getChildren().addAll(structure);
  }

}
