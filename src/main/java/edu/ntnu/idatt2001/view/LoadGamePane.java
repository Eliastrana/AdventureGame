package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.controller.GameFromImport;
import edu.ntnu.idatt2001.controller.SceneSwitcher;
import edu.ntnu.idatt2001.controller.StartGameFromCatalog;
import edu.ntnu.idatt2001.utility.AlertUtil;
import edu.ntnu.idatt2001.utility.SoundPlayer;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Frontend Class for loading games.
 */
public class LoadGamePane extends StackPane {
  private static final List<ImageView> imageViews = new ArrayList<>();
  private static int selectedIndex = -1;
  public static final TextField saveName = new TextField();
  public static final ComboBox<String> comboBoxCharacter = new ComboBox<>();
  public static final ComboBox<String> comboBoxPath = new ComboBox<>();
  public static final ComboBox<String> comboBoxGoals = new ComboBox<>();

  /**
   * Constructor for LoadGamePane.
   * Lets the user pick a path, character, character-image and goal.
   * Then play it.
   * Also allows for imports of own .paths files.
   */
  public LoadGamePane() throws IOException {

    // Defining constants
    String regex = "[.][^.]+$";
    String alertTitle = "Error";
    String alertBoxId = "alertBox";
    String alertHeaderNotStarting = "Could not start game";
    String comboBoxId = "comboBox";

    setStyle("-fx-background-image: url('mainmenubackgroundsmall.jpeg')");

    HBox structure = new HBox();
    HBox content = new HBox();


    structure.getStylesheets().add("/Style.css");
    content.setAlignment(Pos.CENTER);
    content.setSpacing(5);

    File charactersDirectory = new File("src/main/resources/characters/");
    String[] characterFilenames = charactersDirectory.list();
    Set<String> characterNames = new HashSet<>();
    assert characterFilenames != null;
    for (String filename : characterFilenames) {
      String name = filename.replaceFirst(regex, "");
      characterNames.add(name);
    }
    comboBoxCharacter.getItems().addAll(characterNames);
    comboBoxCharacter.setPromptText("Select character");
    comboBoxCharacter.setId(comboBoxId);


    File pathsDirectory = new File("src/main/resources/paths/");
    String[] pathFilenames = pathsDirectory.list();
    Set<String> pathNames = new HashSet<>();
    assert pathFilenames != null;
    for (String filename : pathFilenames) {
      String name = filename.replaceFirst(regex, "");
      pathNames.add(name);
    }
    comboBoxPath.getItems().addAll(pathNames);
    comboBoxPath.setPromptText("Select path");
    comboBoxPath.setId(comboBoxId);


    File goalsDirectory = new File("src/main/resources/savedGoals/");
    String[] goalFilenames = goalsDirectory.list();
    Set<String> goalNames = new HashSet<>();
    assert goalFilenames != null;
    for (String filename : goalFilenames) {
      String name = filename.replaceFirst(regex, "");
      goalNames.add(name);
    }
    comboBoxGoals.getItems().addAll(goalNames);
    comboBoxGoals.setPromptText("Select goal");
    comboBoxGoals.setId(comboBoxId);


    saveName.setPromptText("Enter save name");
    saveName.setId("textField");

    comboBoxCharacter.getSelectionModel()
            .selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> updateSaveGameName());
    comboBoxPath.getSelectionModel()
            .selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> updateSaveGameName());
    comboBoxGoals.getSelectionModel()
            .selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> updateSaveGameName());


    // Defining a play button
    Button playButton = new Button("Play");
    playButton.setId("Pane1ConfirmButton");
    playButton.setOnAction(event -> {
      saveGameAction();
    });


    HBox imageBox = new HBox();
    imageBox.setAlignment(Pos.CENTER);
    File folder = new File("src/main/resources/characterIcons/");
    File[] files = folder.listFiles();
    Set<String> fileNames = new HashSet<>();
    if (files != null) {
      for (File file : files) {
        if (file.isFile()
                && file.getName().endsWith(".png")
                && !fileNames.contains(file.getName())) {
          ImageView imageView = new ImageView(new Image(file.toURI().toString()));
          imageView.setFitWidth(200);
          imageView.setFitHeight(300);
          imageViews.add(imageView);
          fileNames.add(file.getName());
        }
      }
    }

    String arrowButtonId = "ArrowButton";
    final int[] currentImageIndex = {0};
    imageBox.getChildren().add(imageViews.get(currentImageIndex[0]));
    Button leftButton = new Button("<");
    leftButton.setId(arrowButtonId);

    leftButton.setOnAction(event -> {
      if (currentImageIndex[0] > 0) {
        currentImageIndex[0]--;
      } else {
        currentImageIndex[0] = imageViews.size() - 1;
      }
      ImageView currentImage = imageViews.get(currentImageIndex[0]);
      TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), currentImage);
      transition.setFromX(-imageBox.getWidth());
      transition.setToX(0);
      transition.play();
      imageBox.getChildren().set(0, currentImage);

      selectImage(currentImageIndex[0]);
    });

    Button rightButton = new Button(">");
    rightButton.setId(arrowButtonId);
    rightButton.setOnAction(event -> {
      if (currentImageIndex[0] < imageViews.size() - 1) {
        currentImageIndex[0]++;
      } else {
        currentImageIndex[0] = 0;
      }
      ImageView currentImage = imageViews.get(currentImageIndex[0]);
      TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), currentImage);
      transition.setFromX(imageBox.getWidth());
      transition.setToX(0);
      transition.play();
      imageBox.getChildren().set(0, currentImage);

      selectImage(currentImageIndex[0]);
    });

    HBox root = new HBox(imageBox, new HBox(leftButton, rightButton));
    root.setAlignment(Pos.CENTER);
    root.setSpacing(10);
    root.setId(arrowButtonId);

    Button openButton = new Button("Import from desktop");
    openButton.setId("Pane1ConfirmButton");
    openButton.setOnAction(event -> {

      try {

        GameFromImport.gameFromImportMethod();
      } catch (Exception e) {
        AlertUtil.showAlertBoxConfirmation("Error", "Could not import game",
                "An error occurred while trying to import the game. "
                        + e.getMessage());
      }

    });

    VBox backButtonBox = new VBox();
    Button backButton = new Button("Back");
    backButton.setAlignment(Pos.TOP_LEFT);
    backButton.setId("backNavigation");
    backButtonBox.getChildren().add(backButton);
    backButtonBox.setSpacing(10);
    backButtonBox.setPadding(new Insets(10, 10, 10, 10));
    backButtonBox.setAlignment(Pos.TOP_LEFT);

    Image backIcon = new Image(getClass().getResourceAsStream("/iconography/Back.png"));

    ImageView imageViewBack = new ImageView(backIcon);
    imageViewBack.setPreserveRatio(true);
    imageViewBack.setFitWidth(20); // Set the maximum width for the icon
    imageViewBack.setFitHeight(20); // Set the maximum height for the icon

    backButton.setGraphic(imageViewBack);

    backButton.setOnAction(e -> {
      SoundPlayer.play("src/main/resources/sounds/click.wav");
      SceneSwitcher.switchToMainMenu();
      imageViews.clear();
      comboBoxPath.getItems().clear();
      comboBoxCharacter.getItems().clear();
      saveName.clear();
      comboBoxGoals.getItems().clear();
    });
    backButton.setAlignment(Pos.TOP_LEFT);
    VBox comboBoxes = new VBox();
    comboBoxes.getChildren().addAll(comboBoxCharacter, comboBoxPath, comboBoxGoals);
    comboBoxes.setAlignment(Pos.CENTER);
    comboBoxes.setSpacing(10);

    VBox playButtons = new VBox();
    playButtons.getChildren().addAll(playButton, openButton);
    playButtons.setAlignment(Pos.CENTER);
    playButtons.setSpacing(15);

    content.setAlignment(Pos.CENTER);
    content.setSpacing(10);
    VBox characterImageAndButtons = new VBox();
    characterImageAndButtons.getChildren().addAll(imageBox, root);
    characterImageAndButtons.setAlignment(Pos.TOP_LEFT);
    characterImageAndButtons.setSpacing(10);
    characterImageAndButtons.setPadding(new Insets(100, 50, 100, 0));

    VBox inputFields = new VBox();
    inputFields.getChildren().addAll(comboBoxes, saveName, playButtons);
    inputFields.setAlignment(Pos.CENTER);
    inputFields.setSpacing(20);
    inputFields.setPadding(new Insets(100, 100, 100, 0));

    content.getChildren().addAll(characterImageAndButtons, inputFields);
    structure.getChildren().addAll(backButtonBox, content);
    structure.setSpacing(20);
    getChildren().addAll(structure);
  }


  /**
   * Selects the image at the given index and deselects the previously selected image.
   *
   * @param currentIndex The index of the image to select.
   */
  private static void selectImage(int currentIndex) {
    if (selectedIndex >= 0 && selectedIndex < imageViews.size()) {
      ImageView prevSelectedImageView = imageViews.get(selectedIndex);
      prevSelectedImageView.setEffect(null);
    }
    selectedIndex = currentIndex;
    ImageView selectedImageView = imageViews.get(selectedIndex);
    selectedImageView.setEffect(new DropShadow(20, Color.BLACK));
  }


  /**
   * Processes the selected image and returns the file name of the selected image.
   *
   * @return The file name of the selected image.
   */
  public static String processSelectedImage() {
    if (selectedIndex < 0 || selectedIndex >= imageViews.size()) {
      // Select the first image by default
      selectedIndex = 0;
    }
    ImageView selectedImageView = imageViews.get(selectedIndex);
    String imageUrl = selectedImageView.getImage().getUrl();
    int index = imageUrl.lastIndexOf("/") + 1;
    return imageUrl.substring(index);
  }

  /**
   * Updates the save game name based on the selected categories.
   */
  private void updateSaveGameName() {
    String category1 = comboBoxCharacter.getSelectionModel().getSelectedItem();
    String category2 = comboBoxPath.getSelectionModel().getSelectedItem();
    String category3 = comboBoxGoals.getSelectionModel().getSelectedItem();

    saveName.setText(category1 + "_" + category2 + "_" + category3 + "_save");
  }

  /**
   * Checks if the given file name is unique in the given directory.
   *
   * @param fileName
   * @param directoryPath
   * @return
   */
  public boolean isFileNameUnique(String fileName, String directoryPath) {
    File file = new File(directoryPath, fileName);
    boolean fileExists = file.exists() && !file.isDirectory();
    return !fileExists;
  }

  private void saveGameAction() {
    if (saveName.getText().isBlank()) {
      AlertUtil.showAlertBoxError("Empty save filename", "No save filename",
              "Please enter a save name.");
      return;
    }

    String saveNameString = saveName.getText().trim() + ".txt";

    if (isFileNameUnique(saveNameString, "src/main/resources/saveData/")) {
      String saveData = "src/main/resources/saveData/" + saveNameString;
      String pathFile = "src/main/resources/paths/" + comboBoxPath.getValue() + ".paths";
      String characterFile = "src/main/resources/characters/" + comboBoxCharacter.getValue() + ".paths";
      String playerStats = comboBoxCharacter.getValue() + "\n" + comboBoxPath.getValue();
      String goalsFile = "src/main/resources/savedGoals/" + comboBoxGoals.getValue() + ".txt";
      String characterIcon = processSelectedImage();

      try {
        StartGameFromCatalog startGameFromCatalog = new StartGameFromCatalog(saveData,
                pathFile, characterFile, playerStats, goalsFile, characterIcon);
        startGameFromCatalog.startGameFromCatalogMethod();
      } catch (Exception e) {
        AlertUtil.showAlertBoxError("Error", "Could not start game",
                "An error occurred while trying to start the game. " + e.getMessage());

      }

    } else {
      AlertUtil.showAlertBoxError("Error", "Could not start game",
              "A save file with this name already exists. Please enter a different name.");
    }
  }


}

