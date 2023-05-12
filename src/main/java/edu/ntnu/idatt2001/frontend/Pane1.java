package edu.ntnu.idatt2001.frontend;


import edu.ntnu.idatt2001.fileHandling.SaveFileReader;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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

        VBox structure = new VBox();
        structure.getStylesheets().add("/Style.css");
        VBox content = new VBox();
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
                    imageView.setFitWidth(150);
                    imageView.setFitHeight(160);
                    imageViews.add(imageView);
                    fileNames.add(file.getName());
                }
            }
        }
        final int[] currentImageIndex = {0};
        imageBox.getChildren().add(imageViews.get(currentImageIndex[0]));
        imageBox.setOnMouseClicked(event -> {
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





        Button openButton = new Button("Import game from desktop");
        openButton.setId("navigationButton");
        openButton.setOnAction(event -> {

            StartGameFromImport.StartGameFromImportMethod();

        });

        setStyle("-fx-background-color: #a9cade;");

        Button backButton = new Button("Back to Main");
        backButton.setOnAction(e -> {
            SceneSwitcher.switchToMainMenu();
            //getChildren().clear();
            imageViews.clear();
        });
        backButton.setAlignment(Pos.TOP_LEFT);

        HBox comboBoxes = new HBox();
        comboBoxes.getChildren().addAll(comboBoxCharacter, comboBoxPath);
        comboBoxes.setAlignment(Pos.CENTER);
        comboBoxes.setSpacing(20);

        HBox playButtons = new HBox();
        playButtons.getChildren().addAll(playButton, openButton);
        playButtons.setAlignment(Pos.CENTER);
        playButtons.setSpacing(20);

        content.getChildren().addAll(comboBoxes, imageBox, saveName, playButtons);
        content.setAlignment(Pos.BASELINE_CENTER);
        content.setSpacing(20);

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
        selectedImageView.setEffect(new javafx.scene.effect.InnerShadow(50, Color.BLUEVIOLET));
    }

    public static String processSelectedImage() {
        ImageView selectedImageView = imageViews.get(selectedIndex);
        String imageUrl = selectedImageView.getImage().getUrl();
        int index = imageUrl.lastIndexOf("/") + 1;
        return imageUrl.substring(index);
    }


}

