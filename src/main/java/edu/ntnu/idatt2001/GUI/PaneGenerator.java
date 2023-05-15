package edu.ntnu.idatt2001.GUI;

import edu.ntnu.idatt2001.Action.Action;
import edu.ntnu.idatt2001.Game;
import edu.ntnu.idatt2001.Link;
import edu.ntnu.idatt2001.Passage;
import edu.ntnu.idatt2001.fileHandling.FileDashboard;
import edu.ntnu.idatt2001.fileHandling.SaveFileReader;
import edu.ntnu.idatt2001.frontend.Pane1;
import edu.ntnu.idatt2001.frontend.SceneSwitcher;
import edu.ntnu.idatt2001.goals.*;
import edu.ntnu.idatt2001.utility.AlertUtil;
import edu.ntnu.idatt2001.utility.SoundPlayer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static edu.ntnu.idatt2001.frontend.SceneSwitcher.primaryStage;


public class PaneGenerator extends Application {

  private String characterIcon;

  private Game game;
  private int passageCounter = 0;
  private HBox playerInfo;
  private Label titleLabel;
  private Text contentArea;
  private HBox buttonBox;
  HBox topmenuOptions = new HBox();

  VBox topGoals = new VBox();

  VBox topGoalsHealth = new VBox();
  VBox topGoalsGold = new VBox();
  VBox topGoalsScore = new VBox();
  VBox topGoalsInventory = new VBox();

  Label nameLabel = new Label();
  Label healthLabel = new Label();
  Label goldLabel = new Label();
  Label scoreLabel = new Label();
  Label inventoryLabel = new Label();

  ArrayList<Goal> scoreGoals = new ArrayList<>();
  ArrayList<Goal> healthGoals = new ArrayList<>();
  ArrayList<Goal> goldGoals = new ArrayList<>();
  ArrayList<Goal> inventoryGoals = new ArrayList<>();
  String filePath = "src/main/resources/saveData/" + Pane1.saveName.getText()+".txt";

  public PaneGenerator(Game game, String characterIcon) {
    this.game = game;
    this.characterIcon = characterIcon;
  }

  @Override
  public void start(Stage stage) throws IOException {
    writeFirstPassage();
    titleLabel = new Label(game.begin().getTitle());
    titleLabel.setId("title");


    SoundPlayer.playOnLoop("src/main/resources/sounds/ambiance.wav");

    sortGoals();
    displayGoals();

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
        updateGoals();
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
      updateGoals();
      backAction();
    });

    Button helpButton = new Button("Help");
    helpButton.setId("topMenuButton");
    helpButton.setOnAction(e -> {
      SoundPlayer.play("src/main/resources/sounds/click.wav");

        AlertUtil.showAlert("Top Menu Button", "These buttons allow you to navigate back to the start, as well as going back one passage, or quit the game!", 200, 100, primaryStage);
        AlertUtil.showAlert("Title", "This is the title of the passage!", 230, 200, primaryStage);
        AlertUtil.showAlert("Content", "This is the content of the passage!", 250, 600, primaryStage);
        AlertUtil.showAlert("Navigation button", "Click these buttons to make your choice!", 250, 900, primaryStage);
        AlertUtil.showAlert("Your status!", "Here you can see your status within the categories, as well as your current inventory!", 850, 100, primaryStage);


    });


      topmenuOptions.getChildren().addAll(helpButton, restart, backButton, quitButton);

    topmenuOptions.setSpacing(20);
    VBox topInfo = new VBox();
    topInfo.setSpacing(30);

    playerInfo = new HBox();
    updatePlayerInfo();
    playerInfo.setSpacing(20);
    playerInfo.setAlignment(javafx.geometry.Pos.CENTER);
    playerInfo.setId("playerInfo");






    //displayGoals();


    topGoalsHealth.setId("goalsInfo");
    topGoalsGold.setId("goalsInfo");
    topGoalsScore.setId("goalsInfo");
    topGoalsInventory.setId("goalsInfo");


    Text goalsTitle  = new Text("Goals:");
    goalsTitle.setId("goalsInfo");


    topGoals.getChildren().addAll(goalsTitle, topGoalsHealth, topGoalsGold, topGoalsScore, topGoalsInventory);
    topGoals.setSpacing(5);

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
    root.setRight(topGoals);

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
      nameLabel.setText("Player: " + game.getPlayer().getName());
      healthLabel.setText("Health: " + game.getPlayer().getHealth());
      goldLabel.setText("Gold: " + game.getPlayer().getGold());
      scoreLabel.setText("Score: " + game.getPlayer().getScore());

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

      playerInfo.getChildren().addAll(topmenuOptions,
              nameLabel,
              healthLabel,
              goldLabel,
              scoreLabel,
              new Label("Inventory: "),
              itemBox);

    } catch (Exception e) {
      System.err.println("Could not show player info: " + e.getMessage());
    }
  }


  private void restartAction() throws IOException {
    passageCounter = 0;
    updateContentAndButtons(game.begin());
    updatePlayerInfoBasedOnCounter(passageCounter);
    updateInventoryBasedOnCounter(passageCounter);

  }

  private void displayGoals() {
    Text healthGoalTitle = new Text("Health: ");
    healthGoalTitle.setId("goalsInfo");
    Text currentHealth = new Text(game.getPlayer().getHealth() + " / ");
    currentHealth.setId("goalsInfo");

    HBox totalHealthGoals = new HBox();
    HBox healthGoalsHBox = new HBox();

    for (Goal goal : healthGoals) {
      Text goalText = new Text(goal.toString() + " ");
      goalText.setId("goalsInfo");
      if (goal.isFullfilled(game.getPlayer())) {
        goalText.setStyle("-fx-fill: green;");
      } else {
        goalText.setStyle("-fx-fill: red;");
      }
      healthGoalsHBox.getChildren().add(goalText);
    }
    totalHealthGoals.getChildren().addAll(currentHealth, healthGoalsHBox);
    topGoalsHealth.getChildren().addAll(healthGoalTitle, totalHealthGoals);


    Text scoreGoalTitle = new Text("Score: " + "\n");
    scoreGoalTitle.setId("goalsInfo");
    Text currentScore = new Text(game.getPlayer().getScore() + "/");
    currentScore.setId("goalsInfo");

    HBox totalScoreGoals = new HBox();
    HBox scoreGoalsHBox = new HBox();

    for (Goal goal : scoreGoals) {
      Text goalText = new Text(goal.toString() + " ");
      goalText.setId("goalsInfo");
      if (goal.isFullfilled(game.getPlayer())) {
        goalText.setStyle("-fx-fill: green;");
      } else {
        goalText.setStyle("-fx-fill: red;");
      }
      scoreGoalsHBox.getChildren().add(goalText);
    }
    totalScoreGoals.getChildren().addAll(currentScore, scoreGoalsHBox);
    topGoalsScore.getChildren().addAll(scoreGoalTitle, totalScoreGoals);


    Text goldGoalTitle = new Text("Gold: " + "\n");
    goldGoalTitle.setId("goalsInfo");
    Text currentGold = new Text(game.getPlayer().getGold() + "/");
    currentGold.setId("goalsInfo");

    HBox totalGoldGoals = new HBox();
    HBox goldGoalsHBox = new HBox();

    for (Goal goal : goldGoals) {
      Text goalText = new Text(goal.toString() + " ");
      goalText.setId("goalsInfo");
      if (goal.isFullfilled(game.getPlayer())) {
        goalText.setStyle("-fx-fill: green;");
      } else {
        goalText.setStyle("-fx-fill: red;");
      }
      goldGoalsHBox.getChildren().add(goalText);
    }
    totalGoldGoals.getChildren().addAll(currentGold, goldGoalsHBox);
    topGoalsGold.getChildren().addAll(goldGoalTitle, totalGoldGoals);


    Text inventoryGoalTitle = new Text("Inventory: " + "\n");
    inventoryGoalTitle.setId("goalsInfo");
    Text currentInventory = new Text(game.getPlayer().getInventory().size() + "/");
    currentInventory.setId("goalsInfo");

    HBox totalInventoryGoals = new HBox();
    HBox inventoryGoalsHBox = new HBox();

    for (Goal goal : inventoryGoals) {
      Text goalText = new Text(goal.toString() + " ");
      goalText.setId("goalsInfo");
      if (goal.isFullfilled(game.getPlayer())) {
        goalText.setStyle("-fx-fill: green;");
      } else {
        goalText.setStyle("-fx-fill: red;");
      }
      inventoryGoalsHBox.getChildren().add(goalText);
    }
    totalInventoryGoals.getChildren().addAll(currentInventory, inventoryGoalsHBox);
    topGoalsInventory.getChildren().addAll(inventoryGoalTitle, totalInventoryGoals);
  }




  private void sortGoals() {

    Comparator<Goal> comparator = Comparator.comparingInt(goal -> Integer.parseInt(goal.toString()));


    for (Goal goal : game.getGoals()) {
      if (goal instanceof ScoreGoal)  {
        scoreGoals.add(goal);
      } else if (goal instanceof HealthGoal) {
        healthGoals.add(goal);
      } else if (goal instanceof GoldGoal) {
        goldGoals.add(goal);
      } else if (goal instanceof InventoryGoal) {
        inventoryGoals.add(goal);
      }
    }

    scoreGoals.sort(comparator);
    healthGoals.sort(comparator);
    goldGoals.sort(comparator);
    inventoryGoals.sort(comparator);
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
      button.setId("inGameChoiceButton");
      button.setOnAction(event -> {
        for (Action action : link.getActions()) {
          action.execute(game.getPlayer());
          updateGoals();
        }

        Passage nextPassage = game.go(link);
        if (nextPassage != null) {
          game.getPlayer().setLastPassage(nextPassage);
          try {
            passageCounter++;
            FileDashboard.gameSave("C:"
                    + passageCounter
                    + "\n"
                    + "P:"
                    + nextPassage.getTitle(), filePath);
            writeStatus();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
          updateContentAndButtons(nextPassage);
        }
        playerInfo.getChildren().clear();
        updatePlayerInfo();
      });
      buttonBox.getChildren().add(button);
    }
  }

  private void updatePlayerInfoBasedOnCounter(int counter) {
    SaveFileReader reader = new SaveFileReader();
    HashMap<String, Object> passageData = reader.getPassageParameters(filePath, counter);
    System.out.println(passageData);

    if (passageData.containsKey("health") && passageData.get("health") instanceof Integer) {
      game.getPlayer().setHealth((int) passageData.get("health"));
      System.out.println("Updated health");
    }
    if (passageData.containsKey("gold") && passageData.get("gold") instanceof Integer) {
      game.getPlayer().setGold((int) passageData.get("gold"));
      System.out.println("Updated gold");
    }
    if (passageData.containsKey("score") && passageData.get("score") instanceof Integer) {
      game.getPlayer().setScore((int) passageData.get("score"));
      System.out.println("Updated score");
    }
    updatePlayerInfo();
  }

  private void updateInventoryBasedOnCounter(int counter) {
    SaveFileReader reader = new SaveFileReader();
    ArrayList<String> inventory = reader.getInventoryFromCounter(filePath, counter);
    game.getPlayer().setInventory(inventory);
    updatePlayerInfo();
  }


  private void writeStatus() {
    try {
      FileDashboard.gameSave("N:" + game.getPlayer().getName(), filePath);
      FileDashboard.gameSave("H:" + game.getPlayer().getHealth(), filePath);
      FileDashboard.gameSave("G:" + game.getPlayer().getGold(), filePath);
      FileDashboard.gameSave("S:" + game.getPlayer().getScore(), filePath);
      FileDashboard.gameSave("I:" + game.getPlayer().getInventory(), filePath);
      FileDashboard.gameSave("\n", filePath);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Writes the first passage to the save file
   */
  private void writeFirstPassage() {
    try {
      FileDashboard.gameSave("C:"
              + passageCounter + "\nP:"
              + game.getStory().getOpeningPassage().getTitle(), filePath);
    writeStatus();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void backAction() {
    passageCounter--;
    SaveFileReader reader = new SaveFileReader();
    String namePassage = reader.getPassageNameFromCounter(filePath, passageCounter);
    System.out.println("Checking if " + namePassage + " exists");

    if (game.getStory().getOpeningPassage().getTitle().equals(namePassage)) {
      updateContentAndButtons(game.getStory().getOpeningPassage());
      updatePlayerInfoBasedOnCounter(passageCounter);
      updateInventoryBasedOnCounter(passageCounter);

      System.out.println("Found opening passage");
    } else {
      for (Passage passage : game.getStory().getPassages()) {
        if (passage.getTitle().equals(namePassage)) {
          updateContentAndButtons(passage);
          updatePlayerInfoBasedOnCounter(passageCounter);
          updateInventoryBasedOnCounter(passageCounter);
          System.out.println("Found passage: " + passage);
          break;
        }
      }
    }
  }
  private void updateGoals() {
    topGoalsHealth.getChildren().clear();
    topGoalsGold.getChildren().clear();
    topGoalsScore.getChildren().clear();
    topGoalsInventory.getChildren().clear();
    displayGoals();
  }
}

