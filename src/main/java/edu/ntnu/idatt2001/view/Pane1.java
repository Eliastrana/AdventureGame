package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.controller.GameFromImport;
import edu.ntnu.idatt2001.controller.SceneSwitcher;
import edu.ntnu.idatt2001.controller.StartGameFromCatalog;
import edu.ntnu.idatt2001.utility.SoundPlayer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Pane1 extends StackPane {
  private static final List<ImageView> imageViews = new ArrayList<>();
  private static int selectedIndex = -1;
  public static final TextField saveName = new TextField();
  public static final ComboBox<String> comboBoxCharacter = new ComboBox<>();
  public static final ComboBox<String> comboBoxPath = new ComboBox<>();
  public static final ComboBox<String> comboBoxGoals = new ComboBox<>();

  public Pane1() throws IOException {

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


    Button playButton = new Button("Play");
    playButton.setId("Pane1ConfirmButton");
    playButton.setOnAction(event -> {
      if (saveName.getText().isBlank()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().setId(alertBoxId);
        alert.setTitle("Unavailable");
        alert.setHeaderText("No save name");
        alert.setContentText("Please enter a save name");
        alert.showAndWait();
        return;  // exit early
      }

      String saveNameString = saveName.getText().trim() + ".txt";

      if (isFileNameUnique(saveNameString, "src/main/resources/saveData/")) {
        String saveData = "src/main/resources/saveData/"
                + saveNameString;
        String pathFile = "src/main/resources/paths/"
                + comboBoxPath.getValue()
                + ".paths";
        String characterFile = "src/main/resources/characters/"
                + comboBoxCharacter.getValue()
                + ".paths";
        String playerStats = comboBoxCharacter.getValue()
                + "\n" + comboBoxPath.getValue();
        String goalsFile = "src/main/resources/savedGoals/"
                + comboBoxGoals.getValue()
                + ".txt";
        String characterIcon = processSelectedImage();

        StartGameFromCatalog startGameFromCatalog = new StartGameFromCatalog(saveData,
                pathFile, characterFile, playerStats, goalsFile, characterIcon);


        try {
          startGameFromCatalog.startGameFromCatalogMethod();
        } catch (IOException e) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.getDialogPane().setId(alertBoxId);
          alert.setTitle(alertTitle);
          alert.setHeaderText(alertHeaderNotStarting);
          alert.setContentText("An error occurred while trying to start the game. "
                  + "Please try again.");
          alert.showAndWait();
        } catch (IllegalArgumentException e) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.getDialogPane().setId(alertBoxId);
          alert.setTitle(alertTitle);
          alert.setHeaderText(alertHeaderNotStarting);
          alert.setContentText("An error occurred while trying to start the game. "
                  + e.getMessage());
          alert.showAndWait();
        } catch (Exception e) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.getDialogPane().setId(alertBoxId);
          alert.setTitle(alertTitle);
          alert.setHeaderText(alertHeaderNotStarting);
          alert.setContentText("An error occurred while trying to start the game. \n" + e.getMessage());
          alert.showAndWait();
        }
      } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().setId(alertBoxId);
        alert.setTitle("Unavailable");
        alert.setHeaderText("Name already in use");
        alert.setContentText("Save name already exists, please choose another name");
        alert.showAndWait();
      }
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

        GameFromImport.GameFromImportMethod();
      } catch (IOException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().setId(alertBoxId);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertHeaderNotStarting);
        alert.setContentText("An error occurred while trying to import the game. "
                + "Please try again.");
        alert.showAndWait();
      } catch (IllegalArgumentException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().setId(alertBoxId);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertHeaderNotStarting);
        alert.setContentText("An error occurred while trying to import the game. "
                + e.getMessage());
        alert.showAndWait();
      } catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().setId(alertBoxId);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertHeaderNotStarting);
        alert.setContentText("An error occurred while trying to import the game. \n" + e.getMessage());
        alert.showAndWait();
      }

    });

    Button backButton = new Button("Back");
    backButton.setPadding(new Insets(10, 10, 10, 10));
    backButton.setId("backNavigation");

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
    structure.getChildren().addAll(backButton, content);
    structure.setSpacing(20);
    getChildren().addAll(structure);
  }


  private static void selectImage(int currentIndex) {
    if (selectedIndex >= 0 && selectedIndex < imageViews.size()) {
      ImageView prevSelectedImageView = imageViews.get(selectedIndex);
      prevSelectedImageView.setEffect(null);
    }
    selectedIndex = currentIndex;
    ImageView selectedImageView = imageViews.get(selectedIndex);
    selectedImageView.setEffect(new DropShadow(20, Color.BLACK));
  }

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

  private void updateSaveGameName() {
    String category1 = comboBoxCharacter.getSelectionModel().getSelectedItem();
    String category2 = comboBoxPath.getSelectionModel().getSelectedItem();
    String category3 = comboBoxGoals.getSelectionModel().getSelectedItem();

    saveName.setText(category1 + "_" + category2 + "_" + category3 + "_save");
  }

  public boolean isFileNameUnique(String fileName, String directoryPath) {
    File file = new File(directoryPath, fileName);
    boolean fileExists = file.exists() && !file.isDirectory();
    return !fileExists;
  }
}

