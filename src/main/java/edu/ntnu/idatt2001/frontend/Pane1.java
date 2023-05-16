package edu.ntnu.idatt2001.frontend;


import edu.ntnu.idatt2001.fileHandling.SaveFileReader;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.*;



public class Pane1 extends StackPane {

    private static final List<ImageView> imageViews = new ArrayList<>();
    private static int selectedIndex = -1;

    public static TextField saveName = new TextField();

    public static ComboBox<String> comboBoxCharacter = new ComboBox<String>();

    public static ComboBox<String> comboBoxPath = new ComboBox<String>();

    public static ComboBox<String> comboBoxGoals = new ComboBox<String>();


    public Pane1() throws IOException {

        setStyle("-fx-background-image: url('mainmenubackgroundsmall.jpeg')");

        HBox structure = new HBox();
        HBox content = new HBox();
        VBox characterImageAndButtons = new VBox();
        VBox inputFields = new VBox();

        structure.getStylesheets().add("/Style.css");
        content.setAlignment(Pos.CENTER);
        content.setSpacing(5);

        File characters = new File("src/main/resources/characters/");
        String[] filenames = characters.list();
        ArrayList<String> names = new ArrayList<String>(Arrays.asList());

        for (String filename : filenames) {
            String name = filename.replaceFirst("[.][^.]+$", ""); // remove file extension
            names.add(name);
        }
        comboBoxCharacter.getItems().addAll(names);
        comboBoxCharacter.setPromptText("Select character");
        comboBoxCharacter.setId("comboBox");


        File paths = new File("src/main/resources/paths/");
        String[] filenames2 = paths.list();
        ArrayList<String> names2 = new ArrayList<String>(Arrays.asList());
        for (String filename : filenames2) {
            String name = filename.replaceFirst("[.][^.]+$", ""); // remove file extension
            names2.add(name);
        }
        comboBoxPath.getItems().addAll(names2);
        comboBoxPath.setPromptText("Select path");
        comboBoxPath.setId("comboBox");


        File goals = new File("src/main/resources/savedGoals/");
        String[] filenames3 = goals.list();
        ArrayList<String> names3 = new ArrayList<String>(Arrays.asList());
        for (String filename : filenames3) {
            String name = filename.replaceFirst("[.][^.]+$", ""); // remove file extension
            names3.add(name);
        }
        comboBoxGoals.getItems().addAll(names3);
        comboBoxGoals.setPromptText("Select goal");
        comboBoxGoals.setId("comboBox");





        saveName.setPromptText("Enter save name");
        saveName.setId("textField");

        comboBoxCharacter.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateSaveGameName();
        });
        comboBoxPath.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateSaveGameName();
        });
        comboBoxGoals.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateSaveGameName();
        });

        Button playButton = new Button("Play");
        playButton.setId("Pane1ConfirmButton");
        playButton.setOnAction(event -> {

            String saveNameString = saveName.getText().trim()+".txt";
            System.out.println("The file is uniq: "+isFileNameUnique(saveNameString, "src/main/resources/saveData/"));


            if (isFileNameUnique(saveNameString, "src/main/resources/saveData/")) {

                String saveData = "src/main/resources/saveData/" + saveName.getText()+".txt";
                String pathFile = "src/main/resources/paths/" + comboBoxPath.getValue() + ".paths";
                String characterFile = "src/main/resources/characters/" + comboBoxCharacter.getValue() + ".paths";
                String playerStats = comboBoxCharacter.getValue() + "\n"  + comboBoxPath.getValue() ;
                String goalsFile = "src/main/resources/savedGoals/" + comboBoxGoals.getValue()+".txt";
                String characterIcon = processSelectedImage();


                StartGameFromCatalog startGameFromCatalog = new StartGameFromCatalog(saveData, pathFile, characterFile, playerStats, goalsFile, characterIcon);
                try {
                    startGameFromCatalog.startGameFromCatalogMethod();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
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
                if (file.isFile() && file.getName().endsWith(".png") && !fileNames.contains(file.getName())) {
                    ImageView imageView = new ImageView(new Image(file.toURI().toString()));
                    imageView.setFitWidth(200);
                    imageView.setFitHeight(300);
                    imageViews.add(imageView);
                    fileNames.add(file.getName());
                }
            }
        }

        final int[] currentImageIndex = {0};
        imageBox.getChildren().add(imageViews.get(currentImageIndex[0]));
        Button leftButton = new Button("<");
        leftButton.setId("ArrowButton");

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

            // Select the current image
            selectImage(currentImageIndex[0]);
        });

        Button rightButton = new Button(">");
        rightButton.setId("ArrowButton");
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
        root.setId("ArrowButton");

        Button openButton = new Button("Import from desktop");
        openButton.setId("Pane1ConfirmButton");
        openButton.setOnAction(event -> {

            try {

                StartGameFromImport.startGameFromImportMethod();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

        Button backButton = new Button("Back");
        backButton.setPadding(new Insets(10, 10, 10, 10));
        backButton.setId("backNavigation");
        backButton.setOnAction(e -> {
            SceneSwitcher.switchToMainMenu();
            //getChildren().clear();
            imageViews.clear();
            comboBoxPath.getItems().clear();
            comboBoxCharacter.getItems().clear();
            saveName.clear();

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
        characterImageAndButtons.getChildren().addAll(imageBox,root);
        characterImageAndButtons.setAlignment(Pos.TOP_LEFT);
        characterImageAndButtons.setSpacing(10);
        characterImageAndButtons.setPadding(new Insets(100, 50, 100, 0));

        inputFields.getChildren().addAll(comboBoxes, saveName, playButtons);
        inputFields.setAlignment(Pos.CENTER);
        inputFields.setSpacing(20);
        inputFields.setPadding(new Insets(100, 100, 100, 0));

        content.getChildren().addAll(characterImageAndButtons, inputFields);
        structure.getChildren().addAll(backButton, content);
        structure.setSpacing(20);
        getChildren().addAll(structure);
    }


    private void selectImage(int currentIndex) {
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
        System.out.println("Checking file: " + file.getAbsolutePath());  // Print the absolute path of the file
        boolean fileExists = file.exists() && !file.isDirectory();
        System.out.println("File exists: " + fileExists);  // Print whether the file exists
        return !fileExists;
    }
}

