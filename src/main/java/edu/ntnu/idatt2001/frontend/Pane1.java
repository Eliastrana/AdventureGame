package edu.ntnu.idatt2001.frontend;


import edu.ntnu.idatt2001.GUI.PathsFileGUI;
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

public class Pane1 extends StackPane {

    public static TextArea readGameContent = new TextArea();


    public Pane1() {

        VBox structure = new VBox();

        structure.getStylesheets().add("/Style.css");

        FileDashboard fileDashboard = new FileDashboard();

        VBox content = new VBox();

        TextField importGameField = new TextField();
        importGameField.setId("textField");

        readGameContent.setMaxSize(200, 200);
        importGameField.setPromptText("Enter file name");
        Button importGameButton = new Button("Import game");
        importGameButton.setOnAction(e -> {


            readGameContent.setText(fileDashboard.read(importGameField.getText()));
            System.out.println(fileDashboard.read(importGameField.getText()));

        });

        Button startGameButton = new Button("Start game");
        startGameButton.setId("confirmButton");
        startGameButton.setOnAction(e -> {
            String gameContent = readGameContent.getText();
            PathsFileGUI gui = new PathsFileGUI(gameContent);
            try {
                //gui.VBox(primaryStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        setStyle("-fx-background-color: #ffcccc;");

        Text text1 = new Text("Pane 1");
        text1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        Button backButton = new Button("Back to Main");
        backButton.setOnAction(e -> SceneSwitcher.switchToMainMenu());
        backButton.setAlignment(Pos.TOP_LEFT);

        content.getChildren().addAll(importGameField, importGameButton, readGameContent, startGameButton);
        content.setAlignment(Pos.CENTER);
        content.setSpacing(20);

        structure.getChildren().addAll(backButton, content);
        structure.setSpacing(40);

        getChildren().addAll(structure);
    }
}

