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

        saveName.setPromptText("Enter save name");
        saveName.setId("textField");

        Button playButton = new Button("Play");
        playButton.setId("navigationButton");
        playButton.setOnAction(event -> {
            try {

                StartGameFromCatalog.startGameFromCatalogMethod();

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An error occurred");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                throw new RuntimeException(e);
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

            // Select the current image
            selectImage(currentImageIndex[0]);
        });

        HBox root = new HBox(imageBox, new HBox(leftButton, rightButton));
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        root.setId("ArrowButton");



        Button openButton = new Button("Import game from desktop");
        openButton.setId("navigationButton");
        openButton.setOnAction(event -> {


            if (comboBoxCharacter.getValue() == null || saveName.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Empty fields");
                alert.setHeaderText("Please select a character and a save name");
                alert.showAndWait();
                return;
            } else {
                try {
                    StartGameFromImport.StartGameFromImportMethod();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
        comboBoxes.getChildren().addAll(comboBoxCharacter, comboBoxPath);
        comboBoxes.setAlignment(Pos.CENTER);
        comboBoxes.setSpacing(20);

        VBox playButtons = new VBox();
        playButtons.getChildren().addAll(playButton, openButton);
        playButtons.setAlignment(Pos.CENTER);
        playButtons.setSpacing(20);

        content.setAlignment(Pos.CENTER);
        content.setSpacing(20);

        characterImageAndButtons.getChildren().addAll(imageBox,root);

        characterImageAndButtons.setAlignment(Pos.TOP_LEFT);
        characterImageAndButtons.setSpacing(10);
        characterImageAndButtons.setPadding(new Insets(100, 50, 100, 0));



        inputFields.getChildren().addAll(comboBoxes, saveName, playButtons);
        inputFields.setAlignment(Pos.CENTER);
        inputFields.setSpacing(50);
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



}

