package edu.ntnu.idatt2001.GUI;

import edu.ntnu.idatt2001.fileHandling.FileDashboard;
import edu.ntnu.idatt2001.fileHandling.PlayerRegister;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartingPage extends Application {



    public static TextArea readGameContent = new TextArea();

    public static TextArea readCharacterField = new TextArea();

    TextField writeGameSaveName = new TextField();

    TextField  writeGameFieldTitle = new TextField();

    TextArea writeGameContent = new TextArea();

    TextField writeGameButtonText1 = new TextField();
    TextField writeGameButtonTarget1 = new TextField();
    TextField writeGameButtonText2 = new TextField();
    TextField writeGameButtonTarget2 = new TextField();

    public TextField createPlayerName = new TextField();







    TextField importCharacterField = new TextField();





    TextField setPlayerHealth = new TextField();
    TextField setPlayerGold = new TextField();
    TextField setPlayerScore = new TextField();
    TextField setPlayerInventory = new TextField();

    @Override
    public void start(Stage primaryStage) {

        BorderPane border = new BorderPane();

        VBox root = new VBox();
        Scene mainScene = new Scene(border, 1200, 1000);


        importCharacterField.setText("warrior");




        writeGameSaveName.setPromptText("Save name");
        writeGameFieldTitle.setPromptText("Title of game");
        writeGameButtonText1.setPromptText("Button text");
        writeGameButtonTarget1.setPromptText("Button target");
        writeGameButtonText2.setPromptText("Button text");
        writeGameButtonTarget2.setPromptText("Button target");
        mainScene.getStylesheets().add("/Style.css");
        primaryStage.setScene(mainScene);

        VBox playerCreation = new VBox();
        Text createPlayer = new Text("Create player:");
        createPlayer.setId("title");
        createPlayerName.setPromptText("Enter player name");
        setPlayerHealth.setPromptText("Enter player health");
        setPlayerGold.setPromptText("Enter player gold");
        setPlayerScore.setPromptText("Enter player score");
        setPlayerInventory.setPromptText("Enter player inventory");
        Button createPlayerButton = new Button("Create player");
        playerCreation.getChildren().addAll(createPlayer, createPlayerName, setPlayerHealth, setPlayerGold, setPlayerScore, setPlayerInventory, createPlayerButton);

        createPlayerButton.setOnAction(e -> {

            String playerStats = createPlayerName.getText() +" " + setPlayerHealth.getText() + " " + setPlayerGold.getText() + " " + setPlayerScore.getText() + " " + setPlayerInventory.getText();

            PlayerRegister.saveTextToFile(playerStats, "src/main/resources/characters/"+createPlayerName.getText()+".paths");

            System.out.println(playerStats + createPlayerName.getText()+".paths");

            createPlayerName.clear();
            setPlayerHealth.clear();
            setPlayerGold.clear();
            setPlayerScore.clear();
            setPlayerInventory.clear();

        });

        Text title = new Text("Welcome to the Trollgame");
        title.setId("title");

        HBox pageStructure = new HBox();

        VBox importGame = new VBox();
        importCharacterField.setPromptText("Enter character name");
        Button importCharacterButton = new Button("Import character");
        importCharacterButton.setOnAction(e -> {

            readCharacterField.setText(FileDashboard.readCharacter(importCharacterField.getText()));

            //System.out.println(FileDashboard.readCharacter(importCharacterField.getText()));



                }
        );

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

        importGame.getChildren().addAll(importCharacterField,importCharacterButton,readCharacterField , importGameField, importGameButton, readGameContent, startGameButton);
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

        playerCreation.setSpacing(20);
        pageStructure.getChildren().addAll(playerCreation,importGame, writeGame);
        pageStructure.setSpacing(100);
        pageStructure.setAlignment(Pos.CENTER);


        //HBox menu = topMenu(primaryStage);

        root.getChildren().addAll(title, pageStructure);
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(300);


        border.setTop(topMenu(primaryStage));

        border.setCenter(title);

        border.setBottom(pageStructure);


        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public HBox topMenu(Stage primaryStage) {


        String filePath = "src/main/resources/characters/"+importCharacterField.getText()+".paths";
//        PlayerRegister playerRegister = new PlayerRegister();
//        String characterInfo = playerRegister.characterInforVariable(filePath);
        HBox playerStats = new HBox();
        playerStats.setSpacing(10);
        Text playerNameVariable = new Text();

        if (importCharacterField.getText().isBlank()) {
            Text emptyMenu = new Text();
            playerStats.getChildren().addAll(emptyMenu);
        } else {
//            playerNameVariable.setText(characterInfo);
            playerStats.getChildren().addAll(playerNameVariable);
        }

        playerStats.setStyle("-fx-font-size: 30");
        playerStats.getStylesheets().add("Style.css");
        playerStats.setAlignment(Pos.TOP_CENTER);
        playerStats.setId("playerStats");

        return playerStats;
    }
}
