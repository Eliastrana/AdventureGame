package edu.ntnu.idatt2001.frontend;

import static edu.ntnu.idatt2001.frontend.SceneSwitcher.primaryStage;

import edu.ntnu.idatt2001.GUI.PaneGenerator;
import edu.ntnu.idatt2001.Game;
import edu.ntnu.idatt2001.Link;
import edu.ntnu.idatt2001.fileHandling.CreateGame;
import edu.ntnu.idatt2001.fileHandling.SaveFileReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
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

    File savedGamesfolder = new File("src"
            + File.separator
            + "main" + File.separator
            + "resources" + File.separator
            + "saveData");
    File[] listOfFiles = savedGamesfolder.listFiles();

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
        System.out.println("QuickLoad:" + "file:src/main/resources/characterIcons/"
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

            if (gameCreated.getStory().getBrokenLinks().size() != 0) {
              Alert brokenLinks = new Alert(Alert.AlertType.WARNING);
              brokenLinks.setTitle("Warning");
              brokenLinks.setHeaderText("Broken links");
              brokenLinks.setContentText("Number of broken links: "
                      + gameCreated.getStory().getBrokenLinks().size()
                      + "\n" + "The following links are broken: "
                      + gameCreated.getStory().getBrokenLinks());

              ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
              ButtonType removeButton = new ButtonType("Remove", ButtonBar.ButtonData.APPLY);

              brokenLinks.getButtonTypes().addAll(cancelButton, removeButton);

              Optional<ButtonType> result = brokenLinks.showAndWait();
              if (result.isPresent()) {
                if (result.get() == cancelButton) {
                  return;
                } else if (result.get() == removeButton) {
                  List<Link> brokenLinksList = gameCreated.getStory().getBrokenLinks();
                  for (Link link : brokenLinksList) {
                    gameCreated.getStory().removePassage(link);
                  }
                }
              }
            }

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

    return savedGames;

  }

}
