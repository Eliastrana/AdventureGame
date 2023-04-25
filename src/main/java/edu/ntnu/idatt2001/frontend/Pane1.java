package edu.ntnu.idatt2001.frontend;


import edu.ntnu.idatt2001.GUI.PaneGenerator;
import edu.ntnu.idatt2001.GUI.PathsFileGUI;
import edu.ntnu.idatt2001.Game;
import edu.ntnu.idatt2001.fileHandling.CreateGame;
import edu.ntnu.idatt2001.fileHandling.FileDashboard;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

import static edu.ntnu.idatt2001.frontend.SceneSwitcher.primaryStage;

public class Pane1 extends StackPane {

    public static TextField selectCharacterField = new TextField();



    public Pane1() {

        VBox structure = new VBox();

        structure.getStylesheets().add("/Style.css");

        VBox content = new VBox();

        selectCharacterField.setId("textField");
        selectCharacterField.setPromptText("Select character");


        Button openButton = new Button("Open File");
        openButton.setId("navigationButton");
        openButton.setOnAction(event -> {
            // Create a file chooser dialog
            FileChooser fileChooser = new FileChooser();

            // Set the initial directory to the user's desktop
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home"), "Desktop"));

            // Show the dialog and wait for the user to select a file
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                // Handle the selected file here
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                CreateGame game = new CreateGame(selectedFile.getAbsolutePath());
                PaneGenerator gui = null;
                try {
                    gui = new PaneGenerator(game.gameGenerator("src/main/resources/characters/"+selectCharacterField.getText()+".paths"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    gui.start(primaryStage);
                    primaryStage.setFullScreen(true);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        setStyle("-fx-background-color: #a9cade;");

        Button backButton = new Button("Back to Main");
        backButton.setOnAction(e -> SceneSwitcher.switchToMainMenu());
        backButton.setAlignment(Pos.TOP_LEFT);

        content.getChildren().addAll(selectCharacterField, openButton);
        content.setAlignment(Pos.BASELINE_CENTER);
        content.setSpacing(20);

        structure.getChildren().addAll(backButton, content);
        structure.setSpacing(40);

        getChildren().addAll(structure);
    }
}

