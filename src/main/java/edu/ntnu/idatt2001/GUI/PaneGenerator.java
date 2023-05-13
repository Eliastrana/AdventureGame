package edu.ntnu.idatt2001.GUI;

import edu.ntnu.idatt2001.Action.Action;
import edu.ntnu.idatt2001.Game;
import edu.ntnu.idatt2001.Link;
import edu.ntnu.idatt2001.Passage;
import edu.ntnu.idatt2001.Player;
import edu.ntnu.idatt2001.fileHandling.CreateGame;
import edu.ntnu.idatt2001.fileHandling.FileDashboard;
import edu.ntnu.idatt2001.fileHandling.GameSave;
import edu.ntnu.idatt2001.fileHandling.SaveFileReader;
import edu.ntnu.idatt2001.frontend.Pane1;
import edu.ntnu.idatt2001.frontend.SceneSwitcher;
import edu.ntnu.idatt2001.utility.SoundPlayer;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PaneGenerator extends Application {
    private Game game;
    private HBox playerInfo;
    private Label titleLabel;
    private Text contentArea;
    private HBox buttonBox;
    HBox topmenuOptions = new HBox();

    Label nameLabel = new Label();
    Label healthLabel = new Label();
    Label goldLabel = new Label();
    Label scoreLabel = new Label();
    Label inventoryLabel = new Label();
    String filePath = "src/main/resources/saveData/" + Pane1.saveName.getText();
    public PaneGenerator(Game game) {
        this.game = game;
    }

    @Override
    public void start(Stage stage) throws IOException {


        writeFirstPassage();


        titleLabel = new Label(game.begin().getTitle());
        titleLabel.setId("title");

        SoundPlayer.playOnLoop("src/main/resources/sounds/ambiance.wav");

        Pane characterImage = new Pane();
        String imageSource = "characterIcons/" + Pane1.processSelectedImage();
        Image image = new Image(imageSource);
        ImageView imageView = new ImageView(image);
        characterImage.getChildren().add(imageView);

        Button restart = new Button("Restart");
        restart.setId("topMenuButton");
        restart.setOnAction(e -> {
            try {
                restartAction();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });



        Button quitButton = new Button("Quit");
        quitButton.setId("topMenuButton");
        quitButton.setOnAction(e -> {
            try {
                quitGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Quitting game");
        });

        Button backButton = new Button("Back");
        backButton.setId("topMenuButton");
        backButton.setOnAction(e -> {
            backAction();
        });

        topmenuOptions.getChildren().addAll(restart, backButton, quitButton);

        topmenuOptions.setSpacing(20);
        VBox topInfo = new VBox();
        topInfo.setSpacing(30);

        playerInfo = new HBox();
        updatePlayerInfo();
        playerInfo.setSpacing(20);
        playerInfo.setAlignment(javafx.geometry.Pos.CENTER);
        playerInfo.setId("playerInfo");

        contentArea = new Text();
        contentArea.setWrappingWidth(700);
        contentArea.setFill(Color.WHITE); // Set fill color to white
        contentArea.setId("contentArea");

        buttonBox = new HBox();
        buttonBox.getStylesheets().add("/Style.css");
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(javafx.geometry.Pos.CENTER);
        buttonBox.setPadding(new javafx.geometry.Insets(20, 40, 40, 20));
        updateContentAndButtons(game.begin());

        BorderPane root = new BorderPane();

        topInfo.getChildren().addAll(playerInfo, titleLabel);
        topInfo.setSpacing(80);
        topInfo.setAlignment(javafx.geometry.Pos.CENTER);
        topInfo.setPadding(new javafx.geometry.Insets(20, 20, 20, 20));


        root.setTop(topInfo);
        root.setLeft(characterImage);

        root.setCenter(contentArea);
        root.setBottom(buttonBox);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("/Style.css");

        stage.setScene(scene);
        stage.setTitle(game.getStory().getTitle());
        stage.show();
    }

    public void updatePlayerInfo() {
        try {
            playerInfo.getChildren().clear(); // remove existing child nodes
            nameLabel.setText("Player: " + SaveFileReader.getName(filePath));
            healthLabel.setText("Health: " + SaveFileReader.getHealth(filePath));
            goldLabel.setText("Gold: " + SaveFileReader.getGold(filePath));
            scoreLabel.setText("Score: " + SaveFileReader.getScore(filePath));
            inventoryLabel.setText("Inventory: ");
            // create new labels with updated player info

            HBox itemBox = new HBox(); // create HBox to hold item labels and images
            for (String item : game.getPlayer().getInventory()) {
                String imagePath = "src/main/resources/items/" + item + ".png";
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    Image iconImage = new Image(new FileInputStream(imagePath));
                    ImageView iconImageView = new ImageView(iconImage);
                    iconImageView.setFitHeight(50);
                    iconImageView.setFitWidth(50);
                    itemBox.getChildren().add(iconImageView); // add image to itemBox
                } else {
                    Label itemLabel = new Label(item);
                    itemBox.getChildren().add(itemLabel); // add label to itemBox
                }
            }

            itemBox.setSpacing(10);
            itemBox.setAlignment(Pos.CENTER);

            playerInfo.getChildren().addAll(topmenuOptions, nameLabel, healthLabel, goldLabel, scoreLabel, inventoryLabel, itemBox);

        } catch (Exception e) {
            System.err.println("Could not show player info: " + e.getMessage()); // handle specific exception
        }
    }

    private void restartAction() throws IOException {
        updateContentAndButtons(game.getStory().getOpeningPassage());
        playerInfo.getChildren().clear();
        updatePlayerInfo();
    }

    private void quitGame() throws IOException {
        SceneSwitcher.switchToPane1();
    }


    private void updateContentAndButtons(Passage passage) {
        if (passage == null) {
            throw new IllegalArgumentException("Passage cannot be null");
        }
        game.getPlayer().setLastPassage(passage);
        System.out.println("Current passage: " + passage.getTitle());
        titleLabel.setText(passage.getTitle());
        contentArea.setText(passage.getContent());
        buttonBox.getChildren().clear();

        List<Link> links = passage.getLinks();
        for (Link link : links) {
            SoundPlayer.play("src/main/resources/sounds/click.wav");

            Button button = new Button(link.getText());
            button.setId("navigationButton");
            button.setOnAction(event -> {
                for (Action action : link.getActions()) {
                    action.execute(game.getPlayer());
                }

                Passage nextPassage = game.go(link);
                if (nextPassage != null) {
                    game.getPlayer().setLastPassage(nextPassage);
                    try {
                        FileDashboard.gameSave("P:" + nextPassage.getTitle(), filePath);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    updateContentAndButtons(nextPassage);
                    writeStatus();
                }
                playerInfo.getChildren().clear();
                updatePlayerInfo();
            });
            buttonBox.getChildren().add(button);
        }
    }

    private void writeStatus() {
        try {
            FileDashboard.gameSave("N:"+game.getPlayer().getName(), filePath);
            FileDashboard.gameSave("H:"+game.getPlayer().getHealth(), filePath);
            FileDashboard.gameSave("G:"+game.getPlayer().getGold(), filePath);
            FileDashboard.gameSave("S:"+game.getPlayer().getScore(), filePath);
            FileDashboard.gameSave("I:"+game.getPlayer().getInventory(), filePath);
            FileDashboard.gameSave("\n", filePath);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void writeFirstPassage()  {
        try {
            FileDashboard.gameSave("P:" + game.getStory().getOpeningPassage().getTitle(), filePath);
            writeStatus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void backAction(){
        HashMap<String, LinkedList<String>> backOnePassage = SaveFileReader.backOnePassage(filePath);
        System.out.println(backOnePassage);
        String newPassageTitle = backOnePassage.get("P:").getFirst();
        String newName = backOnePassage.get("N:").getFirst();
        int newHealth = Integer.parseInt(backOnePassage.get("H:").getFirst());
        int newGold = Integer.parseInt(backOnePassage.get("G:").getFirst());
        int newScore = Integer.parseInt(backOnePassage.get("S:").getFirst());
        List<String> newInventory = backOnePassage.get("I:");



        nameLabel.setText("Name: " + newName);
        healthLabel.setText("Health: " + newHealth);
        goldLabel.setText("Gold: " + newGold);
        scoreLabel.setText("Score: " + newScore);
        inventoryLabel.setText("Inventory: " + newInventory);

        Passage backPassage = game.getStory().getPassages().stream()
                .filter(pass -> pass.getTitle().equals(newPassageTitle))
                .findFirst()
                .orElse(null);
        System.out.println(backPassage);

        if (backPassage != null) {
            System.out.println("Back to: " + backPassage.getTitle());
        }

        try {
            assert backPassage != null;
            FileDashboard.gameSave("P:"+backPassage.getTitle(), filePath);
            updateContentAndButtons(backPassage);
            playerInfo.getChildren().clear();
            updatePlayerInfo();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }



    public static void main (String[]args){
        launch(args);
    }

}
