package edu.ntnu.idatt2001.controller;

import static edu.ntnu.idatt2001.controller.SceneSwitcher.primaryStage;

import edu.ntnu.idatt2001.model.Game;
import edu.ntnu.idatt2001.utility.AlertUtil;
import edu.ntnu.idatt2001.utility.exceptions.InvalidFormatException;
import edu.ntnu.idatt2001.utility.filehandling.CreateGame;
import edu.ntnu.idatt2001.utility.filehandling.SaveFileReader;
import edu.ntnu.idatt2001.view.PaneGenerator;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * A class that represents a quick load of a game.
 */
public class QuickLoad {
  static final String GAME_SAVE_STYLE = "-fx-fill: #ffffff; -fx-font-size: 10";
  static final String GAME_SAVE_ID = "savedGameText";
  SaveFileReader saveFileReader;
  String characterPath;
  String filePath;
  String goalsPath;
  String characterIcon;

  /**
   * Creates a new quick load with the given characterPath, filePath, goalsPath, and characterIcon.
   *
   * @param characterPath path to the character
   * @param filePath      path to the file
   * @param goalsPath     path to the goals
   * @param characterIcon path to the character icon
   */
  public QuickLoad(String characterPath, String filePath, String goalsPath, String characterIcon) {
    this.characterPath = characterPath;
    this.filePath = filePath;
    this.goalsPath = goalsPath;
    this.characterIcon = characterIcon;
    saveFileReader = new SaveFileReader();
  }

  /**
   * Create a HBox with the saved game information.
   *
   * @param characterPath String path to the character
   * @param filePath     String path to the file
   * @param goalsPath   String path to the goals
   * @param characterIcon String path to the character icon
   *
   * @return HBox with the saved game information
   * @throws IOException if the file is not found
   */
  public static HBox savedNameDisplayer(String characterPath,
                                        String filePath,
                                        String goalsPath,
                                        String characterIcon)
          throws IOException, InvalidFormatException {
    QuickLoad quickLoad = new QuickLoad(characterPath,
            filePath,
            goalsPath,
            characterIcon);
    return quickLoad.savedGamesContainer();
  }

  /**
   * Create a HBox with the saved game information.
   *
   * @return HBox with the saved game information
   * @throws IOException if the file is not found
   */
  public HBox savedGamesContainer() throws IOException, InvalidFormatException {
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
          savedGameCharacter.setStyle(GAME_SAVE_STYLE);

          savedGameCharacter.setId(GAME_SAVE_ID);

          Text savedGamePath = new Text("Chosen path: " + "\n"
                  + saveFileReader.getPath(file.getPath()));
          savedGamePath.setId(GAME_SAVE_ID);
          savedGamePath.setStyle(GAME_SAVE_STYLE);

          Text savedGameCurrentPassage = new Text("Last seen passage: " + "\n"
                  + saveFileReader.getLastSeenPassage(file.getPath()));
          savedGameCurrentPassage.setId(GAME_SAVE_ID);
          savedGameCurrentPassage.setStyle(GAME_SAVE_STYLE);


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
            try {
              characterPath = "src/main/resources/characters/"
                      + saveFileReader.getName(file.getPath())
                      + ".paths";
              filePath = "src/main/resources/paths/"
                      + saveFileReader.getPath(file.getPath())
                      + ".paths";
              goalsPath = "src/main/resources/savedGoals/"
                      + saveFileReader.getGoal(file.getPath())
                      + ".txt";
              characterIcon = saveFileReader.getImage(file.getPath());
              String savedDataPath = file.getPath();

              CreateGame game = new CreateGame(filePath, characterPath, goalsPath, characterIcon);
              Game gameCreated = game.gameGenerator(characterPath);


              // Start the game
              PaneGenerator gui = new PaneGenerator(gameCreated, savedDataPath, characterIcon);
              gui.start(primaryStage);
              primaryStage.setFullScreen(true);

            } catch (Exception exception) {
              AlertUtil.showAlertBoxError("Error", "Error", exception.getMessage());
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
