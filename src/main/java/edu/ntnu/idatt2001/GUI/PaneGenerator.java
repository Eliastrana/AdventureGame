package edu.ntnu.idatt2001.GUI;

import edu.ntnu.idatt2001.Action.Action;
import edu.ntnu.idatt2001.Game;
import edu.ntnu.idatt2001.Link;
import edu.ntnu.idatt2001.Passage;
import edu.ntnu.idatt2001.Player;
import edu.ntnu.idatt2001.fileHandling.CreateGame;
import edu.ntnu.idatt2001.fileHandling.FileDashboard;
import edu.ntnu.idatt2001.fileHandling.GameSave;
import edu.ntnu.idatt2001.frontend.Pane1;
import edu.ntnu.idatt2001.frontend.SceneSwitcher;
import edu.ntnu.idatt2001.utility.SoundPlayer;
import javafx.application.Application;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class PaneGenerator extends Application {
    private Game game;
    private HBox playerInfo;
    private Label titleLabel;
    private Text contentArea;
    private HBox buttonBox;
    HBox topmenuOptions = new HBox();

    ImageView iconImageView = new ImageView();


    Pane itemImage = new Pane();

    Label itemLabel = new Label();





    public PaneGenerator(Game game) {
        this.game = game;
    }

    @Override
    public void start(Stage stage) throws IOException {


        titleLabel = new Label(game.begin().getTitle());
        titleLabel.setId("title");

        SoundPlayer.playOnLoop("src/main/resources/sounds/ambiance.wav");

        Pane characterImage = new Pane();
        String imageSource = "characterIcons/"+ Pane1.processSelectedImage();
        Image image = new Image(imageSource);
        ImageView imageView = new ImageView(image);
        characterImage.getChildren().add(imageView);

        Button restart = new Button("Restart");
        restart.setId("navigationButton");
        restart.setOnAction(e -> {
            try {
                restartGame();
                FileDashboard.gameSave(game.getStory().getOpeningPassage().getTitle(), "src/main/resources/saveData/"+ Pane1.saveName.getText() +".txt");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        Button quitButton = new Button("Quit");
        quitButton.setId("navigationButton");
        quitButton.setOnAction(e -> {
            try {
                quitGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Quitting game");
        });

        topmenuOptions.getChildren().addAll(restart, quitButton);
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
        scene.getStylesheets().add("Style.css");

        stage.setScene(scene);
        stage.setTitle(game.getStory().getTitle());
        stage.show();
    }

    public void updatePlayerInfo() {
        try {
            playerInfo.getChildren().clear(); // remove existing child nodes

            // create new labels with updated player info
            Label nameLabel = new Label("Player: " + game.getPlayer().getName());
            Label healthLabel = new Label("Health: " + game.getPlayer().getHealth());
            Label goldLabel = new Label("Gold: " + game.getPlayer().getGold());
            Label scoreLabel = new Label("Score: " + game.getPlayer().getScore());
            Label inventoryLabel = new Label("Inventory: ");
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

            playerInfo.getChildren().addAll(topmenuOptions, nameLabel, healthLabel, goldLabel, scoreLabel,inventoryLabel, itemBox);

        } catch (Exception e) {
            System.err.println("Could not show player info: " + e.getMessage()); // handle specific exception
        }
    }



    private void restartGame() throws IOException {
        updateContentAndButtons(game.getStory().getOpeningPassage());
        playerInfo.getChildren().clear();

    }



    private void quitGame() throws IOException {
    SceneSwitcher.switchToPane1();
    }


    private void updateContentAndButtons(Passage passage) {
        if (passage == null) {
            throw new IllegalArgumentException("Passage cannot be null");
        }
        game.getPlayer().setLastPassage(passage);
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

                playerInfo.getChildren().clear();
                updatePlayerInfo();
                Passage nextPassage = game.go(link);
                game.getPlayer().setLastPassage(nextPassage);
                updateContentAndButtons(nextPassage);

                try {
                    FileDashboard.gameSave(nextPassage.getTitle(),"src/main/resources/saveData/"+Pane1.saveName.getText()+".txt");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            buttonBox.getChildren().add(button);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
