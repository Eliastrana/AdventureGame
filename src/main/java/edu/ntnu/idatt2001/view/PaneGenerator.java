package edu.ntnu.idatt2001.view;

import static edu.ntnu.idatt2001.controller.SceneSwitcher.primaryStage;

import edu.ntnu.idatt2001.controller.FileDashboard;
import edu.ntnu.idatt2001.model.Game;
import edu.ntnu.idatt2001.model.Link;
import edu.ntnu.idatt2001.model.Passage;
import edu.ntnu.idatt2001.model.action.Action;
import edu.ntnu.idatt2001.model.goals.Goal;
import edu.ntnu.idatt2001.model.goals.GoldGoal;
import edu.ntnu.idatt2001.model.goals.HealthGoal;
import edu.ntnu.idatt2001.model.goals.InventoryGoal;
import edu.ntnu.idatt2001.model.goals.ScoreGoal;
import edu.ntnu.idatt2001.utility.AlertUtil;
import edu.ntnu.idatt2001.utility.SoundPlayer;
import edu.ntnu.idatt2001.utility.filehandling.SaveFileReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PaneGenerator extends Application {
  static final String CLICKSOUND = "src/main/resources/sounds/click.wav";
  static final String ERROR_TITLE = "Error";


  static final String GOALS_INFO = "goalsInfo";
  static final String HEALTH = "health";
  static final String SCORE = "score";
  int initialValue;
  private final String characterIcon;
  private final Game game;
  private int passageCounter;
  private HBox playerInfo;
  private Label titleLabel;
  private Text contentArea;
  private HBox buttonBox;
  HBox topmenuOptions = new HBox();
  Pane topGoalsPane = new Pane();
  VBox topGoals = new VBox();
  VBox topGoalsHealth = new VBox();
  VBox topGoalsGold = new VBox();
  VBox topGoalsScore = new VBox();
  VBox topGoalsInventory = new VBox();
  Label nameLabel = new Label();
  Label healthLabel = new Label();
  Label goldLabel = new Label();
  Label scoreLabel = new Label();
  ProgressBar healthBar = new ProgressBar();

  ArrayList<Goal> scoreGoals = new ArrayList<>();
  ArrayList<Goal> healthGoals = new ArrayList<>();
  ArrayList<Goal> goldGoals = new ArrayList<>();
  ArrayList<Goal> inventoryGoals = new ArrayList<>();
  String saveFilePath;


  /**
   * Constructor for PaneGenerator.
   *
   * @param game          game
   * @param saveFilePath  saveFilePath
   * @param characterIcon characterIcon
   */

  public PaneGenerator(Game game, String saveFilePath, String characterIcon) {
    this.game = game;
    this.characterIcon = characterIcon;
    this.saveFilePath = saveFilePath;
  }

  @Override
  public void start(Stage stage) throws IOException {

    healthBar.setMinWidth(200);

/**
 * Class for generating the panes.
 */

    List<Link> brokenLinks = game.getStory().getBrokenLinks();
    titleLabel = new Label();
    titleLabel.setId("title");
    contentArea = new Text();
    contentArea.setWrappingWidth(700);
    contentArea.setFill(Color.WHITE); // Set fill color to white
    contentArea.setId("contentArea");
    buttonBox = new HBox();
    buttonBox.setId("buttonBox");
    buttonBox = new HBox();
    buttonBox.getStylesheets().add("/Style.css");
    buttonBox.setSpacing(20);
    buttonBox.setAlignment(javafx.geometry.Pos.CENTER);
    buttonBox.setPadding(new javafx.geometry.Insets(20, 40, 40, 20));



    topGoals.setPadding(new javafx.geometry.Insets(5, 5, 5, 5));
    topGoalsPane.getChildren().add(topGoals);
    topGoalsPane.setId("topGoalsPane");
    topGoalsPane.setMaxHeight(450);


    SaveFileReader saveFileReader = new SaveFileReader();
    if (saveFileReader.getFirstPassageExisting(saveFilePath)) {
      String lastSeenPassage = saveFileReader.getLastSeenPassage(saveFilePath);
      for (Passage passage : game.getStory().getPassages()) {
        if (passage.getTitle().equals(lastSeenPassage)) {
          passageCounter = saveFileReader.getCounterFromPassageTitle(saveFilePath, lastSeenPassage);
          updateInventoryBasedOnCounter(passageCounter);
          updatePlayerInfoBasedOnCounter(passageCounter);
          passage.getLinks().forEach(link -> {
            Button button = new Button(link.getText());
            button.setId("button");
            if (brokenLinks.contains(link) || game.getPlayer().isDead()) {
              button.setDisable(true);
            }

             else {
              button.setOnAction(e -> {
                passageCounter++;
                updateInventoryBasedOnCounter(passageCounter);
                updatePlayerInfoBasedOnCounter(passageCounter);
                updateGoals();
                updateContentAndButtons(passage);
              });
            }
            buttonBox.getChildren().add(button);
          });
          updateContentAndButtons(passage);
        }
      }

    } else {
      passageCounter = 0;
      writeFirstPassage();
      updateContentAndButtons(game.begin());
      titleLabel = new Label(game.begin().getTitle());
      titleLabel.setId("title");
    }

    SoundPlayer.playOnLoop("src/main/resources/sounds/ambiance.wav");
    sortGoals();
    displayGoals();
    Pane characterImage = new Pane();

    if (characterIcon != null) {
      Image image = new Image("/characterIcons/" + characterIcon);
      ImageView imageView = new ImageView(image);
      characterImage.getChildren().add(imageView);
    }


    String topMenuButtonId = "topMenuButton";
    Button restart = new Button("Restart");
    restart.setId(topMenuButtonId);

    restart.setOnAction(e -> {
      SoundPlayer.play(CLICKSOUND);
      restartAction();
      updateGoals();
      healthBar.setProgress(progressBar());
    });

    Button quitButton = new Button("Quit");
    quitButton.setId(topMenuButtonId);
    quitButton.setOnAction(e -> {
      try {
        SoundPlayer.play(CLICKSOUND);
        quitGame(stage);

      } catch (IOException ex) {
        AlertUtil.showAlert(ERROR_TITLE, "Could not quit game", 200, 100, primaryStage);
      }
    });

    Button backButton = new Button("Back");
    backButton.setId(topMenuButtonId);
    backButton.setOnAction(e -> {
      SoundPlayer.play(CLICKSOUND);
      updateGoals();
      backAction();
      healthBar.setProgress(progressBar());
    });

    Button helpButton = new Button("Help");
    helpButton.setId(topMenuButtonId);
    helpButton.setOnAction(e -> {
      SoundPlayer.play(CLICKSOUND);

      AlertUtil.showAlert("Top Menu Button",
              "These buttons allow you to navigate back to the start,"
                      + " as well as going back one passage,"
                      + " or quit the game!", 200, 100, primaryStage);
      AlertUtil.showAlert("Title", "This is the title of the passage!",
              230, 200, primaryStage);
      AlertUtil.showAlert("Content", "This is the content " + "of the passage!",
              250, 600, primaryStage);
      AlertUtil.showAlert("Navigation button",
              "Click these buttons to make your choice!",
              250, 900, primaryStage);
      AlertUtil.showAlert("Your status!",
              "Here you can see your status within the categories,"
                      + " as well as your current inventory!",
              850, 100, primaryStage);

      AlertUtil.showAlert("Goals",
              "Here you can see your goals for the game!",
              850, 500, primaryStage);
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
    topGoalsHealth.setId(GOALS_INFO);
    topGoalsGold.setId(GOALS_INFO);
    topGoalsScore.setId(GOALS_INFO);
    topGoalsInventory.setId(GOALS_INFO);
    Text goalsTitle = new Text("Goals:");
    goalsTitle.setId(GOALS_INFO);

    topGoals.getChildren().addAll(goalsTitle, topGoalsHealth,
            topGoalsGold, topGoalsScore, topGoalsInventory);
    topGoals.setSpacing(5);


    topInfo.getChildren().addAll(playerInfo, titleLabel);
    topInfo.setSpacing(80);
    topInfo.setAlignment(javafx.geometry.Pos.CENTER);
    topInfo.setPadding(new javafx.geometry.Insets(20, 20, 20, 20));

    BorderPane root = new BorderPane();
    root.setTop(topInfo);
    root.setLeft(characterImage);
    root.setRight(topGoalsPane);
    root.setCenter(contentArea);
    root.setBottom(buttonBox);

    Scene scene = new Scene(root, 800, 600);
    scene.getStylesheets().add("/Style.css");
    stage.setScene(scene);
    stage.setTitle(game.getStory().getTitle());
    stage.show();
  }

  /**
   * Updates the player info based on the passage counter.
   */

  public void updatePlayerInfo() {

    try {
      playerInfo.getChildren().clear();
      nameLabel.setText("Player: " + game.getPlayer().getName());
      healthLabel.setText("Health: " + game.getPlayer().getHealth());
      goldLabel.setText("Gold: " + game.getPlayer().getGold());
      scoreLabel.setText("Score: " + game.getPlayer().getScore());

      healthBar.setProgress(progressBar());

      HBox itemBox = new HBox();
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
              healthBar,
              goldLabel,
              scoreLabel,
              new Label("Inventory: "),
              itemBox);


      if (game.getPlayer().isDead()) {
        game.getPlayer().setHealth(0);
        titleLabel.setText("You died!");
        contentArea.setText("");
      } else if (game.getPlayer().isBroke()) {
        game.getPlayer().setGold(0);
        contentArea.setText("You are broke!");
      }

    } catch (Exception e) {
      AlertUtil.showAlert(ERROR_TITLE, e.getMessage(), 250, 600, primaryStage);

    }
  }

  private void restartAction() {
    passageCounter = 0;
    updateContentAndButtons(game.begin());
    updatePlayerInfoBasedOnCounter(passageCounter);
    updateInventoryBasedOnCounter(passageCounter);

  }

  private void displayGoals() {
    Text healthGoalTitle = new Text("Health: ");
    healthGoalTitle.setId(GOALS_INFO);
    Text currentHealth = new Text(game.getPlayer().getHealth() + " / ");
    goalDescription(healthGoalTitle, currentHealth, healthGoals, topGoalsHealth);

    Text scoreGoalTitle = new Text("Score: " + "\n");
    scoreGoalTitle.setId(GOALS_INFO);
    Text currentScore = new Text(game.getPlayer().getScore() + "/");
    goalDescription(scoreGoalTitle, currentScore, scoreGoals, topGoalsScore);

    Text goldGoalTitle = new Text("Gold: " + "\n");
    goldGoalTitle.setId(GOALS_INFO);
    Text currentGold = new Text(game.getPlayer().getGold() + "/");
    goalDescription(goldGoalTitle, currentGold, goldGoals, topGoalsGold);

    Text inventoryGoalTitle = new Text("Inventory: " + "\n");
    inventoryGoalTitle.setId(GOALS_INFO);
    Text currentInventory = new Text(game.getPlayer().getInventory().size() + "/");
    goalDescription(inventoryGoalTitle, currentInventory, inventoryGoals, topGoalsInventory);
  }

  private void goalDescription(Text goldGoalTitle, Text currentGold,
                               ArrayList<Goal> goldGoals, VBox topGoalsGold) {
    currentGold.setId(GOALS_INFO);
    HBox totalGoldGoals = new HBox();
    HBox goldGoalsHbox = new HBox();

    for (Goal goal : goldGoals) {
      Text goalText = new Text(goal.toString() + " ");
      goalText.setId(GOALS_INFO);
      if (goal.isFullfilled(game.getPlayer())) {
        goalText.setStyle("-fx-fill: green;");
      } else {
        goalText.setStyle("-fx-fill: red;");
      }
      goldGoalsHbox.getChildren().add(goalText);
    }
    totalGoldGoals.getChildren().addAll(currentGold, goldGoalsHbox);
    topGoalsGold.getChildren().addAll(goldGoalTitle, totalGoldGoals);
  }

  private void sortGoals() {

    Comparator<Goal> comparator = Comparator.comparingInt(goal
            -> Integer.parseInt(goal.toString()));

    for (Goal goal : game.getGoals()) {
      if (goal instanceof ScoreGoal) {
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

  private void quitGame(Stage stage) throws IOException {

    primaryStage.setFullScreen(false);
    primaryStage.close();

    //This should probably be done in a better way
    Pane1.comboBoxPath.getItems().clear();
    Pane1.comboBoxPath.setPromptText("Select path");
    Pane1.comboBoxCharacter.getItems().clear();
    Pane1.comboBoxCharacter.setPromptText("Select character");
    Pane1.comboBoxGoals.getItems().clear();
    Pane1.comboBoxGoals.setPromptText("Select goal");

    MasterPane gui = new MasterPane();
    gui.start(stage);
  }

  private void updateContentAndButtons(Passage passage) {
    List<Link> brokenLinks = game.getStory().getBrokenLinks();
    if (passage == null) {
      throw new IllegalArgumentException("Passage cannot be null");
    }

    game.getPlayer().setLastPassage(passage);
    titleLabel.setText(passage.getTitle());
    contentArea.setText(passage.getContent());
    buttonBox.getChildren().clear();
    List<Link> links = passage.getLinks();
    for (Link link : links) {
      SoundPlayer.play(CLICKSOUND);
      Button button = new Button(link.getText());
      button.setId("inGameChoiceButton");
      if (brokenLinks.contains(link) || game.getPlayer().isDead()) {
        button.setDisable(true);
      } else {
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
                      + nextPassage.getTitle(), saveFilePath);
              writeStatus();
            } catch (IOException e) {
              AlertUtil.showAlert(ERROR_TITLE, e.getMessage(), 250, 600, primaryStage);

            }
            updateContentAndButtons(nextPassage);
          }
          playerInfo.getChildren().clear();
          updatePlayerInfo();
        });
      }
      buttonBox.getChildren().add(button);
    }
  }

  private void updatePlayerInfoBasedOnCounter(int counter) {

    healthBar.setProgress(progressBar());

    SaveFileReader reader = new SaveFileReader();
    HashMap<String, Object> passageData = reader.getPassageParameters(saveFilePath, counter);

    if (passageData.containsKey(HEALTH) && passageData.get(HEALTH) instanceof Integer) {
      game.getPlayer().setHealth((int) passageData.get(HEALTH));
    }
    if (passageData.containsKey("gold") && passageData.get("gold") instanceof Integer) {
      game.getPlayer().setGold((int) passageData.get("gold"));
    }
    if (passageData.containsKey(SCORE) && passageData.get(SCORE) instanceof Integer) {
      game.getPlayer().setScore((int) passageData.get(SCORE));
    }
    updatePlayerInfo();
  }

  private void updateInventoryBasedOnCounter(int counter) {
    SaveFileReader reader = new SaveFileReader();
    ArrayList<String> inventory = reader.getInventoryFromCounter(saveFilePath, counter);
    game.getPlayer().setInventory(inventory);
    updatePlayerInfo();
  }

  private void writeStatus() {
    try {
      FileDashboard.gameSave("N:" + game.getPlayer().getName(), saveFilePath);
      FileDashboard.gameSave("H:" + game.getPlayer().getHealth(), saveFilePath);
      FileDashboard.gameSave("G:" + game.getPlayer().getGold(), saveFilePath);
      FileDashboard.gameSave("S:" + game.getPlayer().getScore(), saveFilePath);
      FileDashboard.gameSave("I:" + game.getPlayer().getInventory(), saveFilePath);
      FileDashboard.gameSave("\n", saveFilePath);

    } catch (IOException e) {
      AlertUtil.showAlert(ERROR_TITLE, e.getMessage(), 250, 600, primaryStage);

    }
  }

  private void writeFirstPassage() {
    try {
      FileDashboard.gameSave("C:"
              + passageCounter + "\nP:"
              + game.getStory().getOpeningPassage().getTitle(), saveFilePath);
      writeStatus();
    } catch (IOException e) {
      AlertUtil.showAlert(ERROR_TITLE, e.getMessage(), 250, 600, primaryStage);

    }
  }

  private void backAction() {
    passageCounter--;
    updatePlayerInfoBasedOnCounter(passageCounter);
    updateInventoryBasedOnCounter(passageCounter);
    updatePlayerInfo();
    updateGoals();

    SaveFileReader reader = new SaveFileReader();
    String namePassage = reader.getPassageNameFromCounter(saveFilePath, passageCounter);


    if (game.getStory().getOpeningPassage().getTitle().equals(namePassage)) {
      updateContentAndButtons(game.getStory().getOpeningPassage());


    } else {
      for (Passage passage : game.getStory().getPassages()) {
        if (passage.getTitle().equals(namePassage)) {
          updateContentAndButtons(passage);
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

  private double progressBar() {

    if (initialValue == 0) {
      initialValue = game.getPlayer().getHealth();
    }

    int currentValue = game.getPlayer().getHealth();

    double fillPercentage = (double) currentValue / initialValue; // Multiply by 100

    if (fillPercentage < 0.3) {
      healthBar.setStyle("-fx-accent: #ef3232;");
    } else if (fillPercentage < 0.6) {
      healthBar.setStyle("-fx-accent: #e6b800;");
    } else {
      healthBar.setStyle("-fx-accent: #00b300;");
    }

    return fillPercentage;
  }
}