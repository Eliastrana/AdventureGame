package edu.ntnu.idatt2001.frontend;

import edu.ntnu.idatt2001.fileHandling.FileDashboard;
import edu.ntnu.idatt2001.utility.SoundPlayer;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Pane2 extends StackPane {

    public Pane2() {
        setStyle("-fx-background-image: url('mainmenubackgroundsmall.jpeg')");

        VBox structure = new VBox();
        structure.getStylesheets().add("Style.css");
        structure.setSpacing(10);
        VBox vBox = new VBox();

        VBox gameCreation = new VBox();
        gameCreation.setAlignment(Pos.CENTER);
        gameCreation.setSpacing(10);
        gameCreation.getStylesheets().add("Style.css");

        TextField writeGameSaveName = new TextField();
        writeGameSaveName.setPromptText("Write game save name");
        writeGameSaveName.setId("textField");

        TextField writeGameFieldTitle = new TextField();
        writeGameFieldTitle.setPromptText("Write game field title");
        writeGameFieldTitle.setId("textField");

        TextArea writeGameContent = new TextArea();
        writeGameContent.setPromptText("Write game content");
        writeGameContent.setId("textArea");

        HBox gameButton1 = new HBox();
        TextField writeGameButtonText1 = new TextField();
        writeGameButtonText1.setPromptText("Write game button text 1");
        writeGameButtonText1.setId("textField");
        TextField writeGameButtonTarget1 = new TextField();
        writeGameButtonTarget1.setPromptText("Write game button target 1");
        writeGameButtonTarget1.setId("textField");
        gameButton1.getChildren().addAll(writeGameButtonText1, writeGameButtonTarget1);
        gameButton1.setAlignment(Pos.CENTER);
        gameButton1.setSpacing(10);

        HBox gameButton2 = new HBox();
        TextField writeGameButtonText2 = new TextField();
        writeGameButtonText2.setPromptText("Write game button text 2");
        writeGameButtonText2.setId("textField");

        TextField writeGameButtonTarget2 = new TextField();
        writeGameButtonTarget2.setPromptText("Write game button target 2");
        writeGameButtonTarget2.setId("textField");
        gameButton2.setAlignment(Pos.CENTER);
        gameButton2.setSpacing(10);

        gameButton2.getChildren().addAll(writeGameButtonText2, writeGameButtonTarget2);

        HBox actionButtonHBox = new HBox();
        actionButtonHBox.setSpacing(10);
        actionButtonHBox.setAlignment(Pos.CENTER);

        ComboBox<String> actionComboBox = new ComboBox<>();
        actionComboBox.getItems().addAll("HealthAction", "GoldAction", "ScoreAction", "InventoryAction");
        actionComboBox.setPromptText("Choose action");
        actionComboBox.setId("comboBox");

        TextField writeActionTarget = new TextField();
        writeActionTarget.setPromptText("Write action sum: ");
        writeActionTarget.setId("textField");

        actionButtonHBox.getChildren().addAll(actionComboBox, writeActionTarget);

        Button writeGameButton = new Button("Write game");
        writeGameButton.setAlignment(Pos.CENTER);
        writeGameButton.setId("navigationButton");
        writeGameButton.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        writeGameButton.setOnAction(e -> {
            SoundPlayer.play("src/main/resources/sounds/click.wav");
            String fileName = writeGameSaveName.getText(); // add .paths extension to filename

            if (fileName.isBlank()) {
                showAlert("Error", "Empty save name", "Please enter a valid save name");
                return;
            }

            String titleAndText = ":: " + writeGameFieldTitle.getText() + "\n" + writeGameContent.getText() + "\n";
            String buttonAndLink = "";

            String buttonAndLink1 = "[" + writeGameButtonText1.getText() + "]" + "(" + writeGameButtonTarget1.getText() + ")";
            String buttonAndLink2 = "[" + writeGameButtonText2.getText() + "]" + "(" + writeGameButtonTarget2.getText() + ")";

            String actions = "{" + actionComboBox.getValue() + ": " + writeActionTarget.getText() + "}";

            if (writeGameButtonText1.getText().isBlank() || writeGameButtonTarget1.getText().isBlank()) {
                buttonAndLink1 = "";
            }
            if (writeGameButtonText2.getText().isBlank() || writeGameButtonTarget2.getText().isBlank()) {
                buttonAndLink2 = "";
            }

            buttonAndLink = buttonAndLink1 + "\n" + buttonAndLink2;
            String gameContent = titleAndText + buttonAndLink + "\n" + actions;
            System.out.println(gameContent);

            FileDashboard.write(gameContent, fileName); // pass the game content and filename to the write method

            writeGameSaveName.setText("");
            writeGameFieldTitle.setText("");
            writeGameContent.setText("");
            writeGameButtonText1.setText("");
            writeGameButtonTarget1.setText("");
            writeGameButtonText2.setText("");
            writeGameButtonTarget2.setText("");
            writeActionTarget.setText("");
            actionComboBox.setValue(null);

            System.out.println("Game saved to " + fileName);

            showAlert("Success", "Game created!", "You can find your game in the path selector in Load Game");

            System.out.println(gameContent);
        });

        gameCreation.getChildren().addAll(writeGameSaveName, writeGameFieldTitle, writeGameContent, gameButton1, gameButton2, actionButtonHBox, writeGameButton);

        vBox.getChildren().addAll(gameCreation);

        HBox topbar = new HBox();

        Button backButton = new Button("Back");
        backButton.setId("backNavigation");
        backButton.setAlignment(Pos.TOP_LEFT);
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
            alert.setContentText("Write a name for your game save, a title for your game field, the content of your game field and the text and target of the buttons. \n" +
                    "If you only want one button, leave the other button text and target blank. \n" +
                    "When you are done, press the Write game button and your game will be saved in the path selector in Load Game. \n" +
                    "As long as the save name is the same, it will save your passage in the same place");
            alert.showAndWait();
        });

        helpButton.setAlignment(Pos.TOP_RIGHT);

        topbar.getChildren().addAll(backButton, helpButton);
        topbar.setSpacing(15);
        topbar.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        structure.getChildren().addAll(topbar, vBox);

        getChildren().addAll(structure);
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
