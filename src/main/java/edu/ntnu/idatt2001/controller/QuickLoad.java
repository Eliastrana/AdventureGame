package edu.ntnu.idatt2001.controller;

import static edu.ntnu.idatt2001.controller.SceneSwitcher.primaryStage;

import edu.ntnu.idatt2001.model.Game;
import edu.ntnu.idatt2001.utility.filehandling.CreateGame;
import edu.ntnu.idatt2001.utility.filehandling.SaveFileReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import edu.ntnu.idatt2001.utility.SoundPlayer;
import edu.ntnu.idatt2001.view.PaneGenerator;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class QuickLoad {
  SaveFileReader saveFileReader;
  String characterPath;
  String filePath;
  String goalsPath;
  String characterIcon;

  public QuickLoad(String characterPath, String filePath, String goalsPath, String characterIcon) {
    this.characterPath = characterPath;
    this.filePath = filePath;
    this.goalsPath = goalsPath;
    this.characterIcon = characterIcon;
    saveFileReader = new SaveFileReader();
  }

  public String getFilePath() {

    return filePath;
  }

  public static HBox savedNameDisplayer(String characterPath,
                                        String filePath,
                                        String goalsPath,
                                        String characterIcon) throws IOException {
    QuickLoad quickLoad = new QuickLoad(characterPath,
            filePath,
            goalsPath,
            characterIcon);
    return quickLoad.savedGamesContainer();
  }

  public HBox savedGamesContainer() throws IOException {


    HBox savedGames = new HBox();
    savedGames.setAlignment(Pos.CENTER);
    savedGames.setSpacing(15);

    File savedGamesFolder = new File("src/main/resources/saveData/");
    File[] listOfFiles = savedGamesFolder.listFiles();

    if (listOfFiles != null) { // Add a null check before accessing the array
      Arrays.sort(listOfFiles, Comparator.comparingLong(File::lastModified).reversed());

    int count = 0;
    for (File file : listOfFiles) {
      if (file.isFile() && count < 3) { // Only process the first three files
        StackPane pane = new StackPane();
        pane.setId("savedGamePane");

        Rectangle background = new Rectangle();
        background.setId("savedGamePane");



        Text savedGameCharacter = new Text("Character name: "
                + "\n" + saveFileReader.getName(file.getPath()));
        savedGameCharacter.setStyle("-fx-fill: #ffffff; -fx-font-size: 10");

        savedGameCharacter.setId("savedGameText");

        Text savedGamePath = new Text("Chosen path: " + "\n"
                + saveFileReader.getPath(file.getPath()));
        savedGamePath.setId("savedGameText");
        savedGamePath.setStyle("-fx-fill: #ffffff; -fx-font-size: 10");

        Text savedGameCurrentPassage = new Text("Last seen passage: " + "\n"
                + saveFileReader.getLastSeenPassage(file.getPath()));
        savedGameCurrentPassage.setId("savedGameText");
        savedGameCurrentPassage.setStyle("-fx-fill: #ffffff; -fx-font-size: 10");


        Image image = new Image("file:src/main/resources/characterIcons/"
                + saveFileReader.getImage(file.getPath()));
        ImageView savedGameImage = new ImageView(image);
        savedGameImage.setFitHeight(100);
        savedGameImage.setFitWidth(60);
        savedGameImage.setPreserveRatio(true);

        VBox savedGameContent = new VBox();
        savedGameContent.setAlignment(Pos.BASELINE_RIGHT);
        savedGameContent.getChildren().addAll(savedGameCharacter,
                savedGamePath,
                savedGameCurrentPassage);

        HBox savedGameContentStructure = new HBox();
        savedGameContentStructure.getChildren().addAll(savedGameContent, savedGameImage);
        pane.getChildren().addAll(savedGameContentStructure);

        pane.setOnMouseClicked(e -> {
          SoundPlayer.play("src/main/resources/sounds/click.wav");
          try {
            String characterPath = "src/main/resources/characters/"
                    + saveFileReader.getName(file.getPath())
                    + ".paths";
            String filePath = "src/main/resources/paths/"
                    + saveFileReader.getPath(file.getPath())
                    + ".paths";
            String goalsPath = "src/main/resources/savedGoals/"
                    + saveFileReader.getGoal(file.getPath())
                    + ".txt";
            String characterIcon = saveFileReader.getImageIcon(file.getPath());
            String savedDataPath = file.getPath();

            CreateGame game = new CreateGame(filePath, characterPath, goalsPath, characterIcon);
            Game gameCreated = game.gameGenerator(characterPath);


            // Start the game
            PaneGenerator gui = new PaneGenerator(gameCreated, savedDataPath, characterIcon);
            gui.start(primaryStage);
            primaryStage.setFullScreen(true);

          } catch (Exception exception) {
            exception.printStackTrace();
          }
        });
        pane.getChildren().addAll(background, savedGameContent);
        savedGames.getChildren().add(pane);
        count++;
      }
    }
  }
    return savedGames;
  }
}
