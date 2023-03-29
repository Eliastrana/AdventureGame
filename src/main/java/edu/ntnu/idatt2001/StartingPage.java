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

import java.io.FileWriter;
import java.io.IOException;

public class StartingPage extends Application {

    public static TextArea readGameContent = new TextArea();

    TextField writeGameSaveName = new TextField();

    TextField  writeGameFieldTitle = new TextField();

    TextArea writeGameContent = new TextArea();

    TextField writeGameButtonText1 = new TextField();

    TextField writeGameButtonTarget1 = new TextField();

    TextField writeGameButtonText2 = new TextField();

    TextField writeGameButtonTarget2 = new TextField();



    @Override
    public void start(Stage primaryStage) {

        VBox root = new VBox();


        Scene mainScene = new Scene(root, 1000, 700);

        writeGameSaveName.setPromptText("Save name");

        writeGameFieldTitle.setPromptText("Title of game");

        writeGameButtonText1.setPromptText("Button text");

        writeGameButtonTarget1.setPromptText("Button target");

        writeGameButtonText2.setPromptText("Button text");

        writeGameButtonTarget2.setPromptText("Button target");

        mainScene.getStylesheets().add("/Style.css");

        primaryStage.setScene(mainScene);


        Text title = new Text("Welcome to the Trollgame");
        title.setId("title");

        HBox pageStructure = new HBox();

        VBox importGame = new VBox();
        TextField importGameField = new TextField();
        importGameField.setPromptText("Enter file name");
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

        HBox firstButtonInfo = new HBox();
        firstButtonInfo.getChildren().addAll(writeGameButtonText1, writeGameButtonTarget1);

        HBox secondButtonInfo = new HBox();
        secondButtonInfo.getChildren().addAll(writeGameButtonText2, writeGameButtonTarget2);


        VBox writeGame = new VBox();

        Button writeGameButton = new Button("Write game");
        writeGameButton.setOnAction(e -> {
            String fileName = writeGameSaveName.getText(); // add .paths extension to filename
            String gameContent = writeGameFieldTitle.getText() +"\n"+ writeGameContent.getText() +"\n"+"["+ writeGameButtonText1.getText() + "]"+"("+writeGameButtonTarget1.getText() +")"+"["+ writeGameButtonText2.getText() +"]"+"("+ writeGameButtonTarget2.getText()+")";

            FileDashboard.write(gameContent, fileName); // pass the game content and filename to the write method
            writeGameFieldTitle.setText("");
            writeGameContent.setText("");
            writeGameButtonText1.setText("");
            writeGameButtonTarget1.setText("");
            writeGameButtonText2.setText("");
            writeGameButtonTarget2.setText("");

            System.out.println("Game saved to " + fileName);
        });

        Button saveGameButton = new Button("Add button");
        saveGameButton.setOnAction(e -> {

        });



        writeGame.getChildren().addAll(writeGameSaveName, writeGameFieldTitle, writeGameContent,firstButtonInfo,secondButtonInfo,writeGameButton ,saveGameButton);

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
