package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.fileHandling.FileDashboard;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartingPage extends Application {

    public static TextArea readGameContent = new TextArea();

    @Override
    public void start(Stage primaryStage) {

        VBox root = new VBox();
        Scene mainScene = new Scene(root, 1000, 700);
        primaryStage.setScene(mainScene);


        Text title = new Text("Welcome to the Trollgame");

        HBox pageStructure = new HBox();

        VBox importGame = new VBox();
        TextField importGameField = new TextField("Choose game");
        Button importGameButton = new Button("Import game");
        importGameButton.setOnAction(e -> {


            readGameContent.setText(FileDashboard.read(importGameField.getText()));

            System.out.println(FileDashboard.read(importGameField.getText()));

        });

        Button startGameButton = new Button("Start game");
        startGameButton.setOnAction(e -> {
            String gameContent = readGameContent.getText();
            PathsFileGUI gui = new PathsFileGUI(gameContent);
            try {
                gui.VBox(primaryStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });


        importGame.getChildren().addAll(importGameField, importGameButton, readGameContent, startGameButton);

        VBox writeGame = new VBox();
        TextField  writeGameField = new TextField("Write game");
        Button writeGameButton = new Button("Write game");
        writeGame.getChildren().addAll(writeGameField, writeGameButton);

        pageStructure.getChildren().addAll(importGame, writeGame);
        pageStructure.setSpacing(100);
        pageStructure.setAlignment(Pos.CENTER);

        root.getChildren().addAll(title, pageStructure);
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(300);






















        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
