package edu.ntnu.idatt2001.frontend;


import edu.ntnu.idatt2001.GUI.PaneGenerator;
import edu.ntnu.idatt2001.GUI.PathsFileGUI;
import edu.ntnu.idatt2001.Game;
import edu.ntnu.idatt2001.fileHandling.CreateGame;
import edu.ntnu.idatt2001.fileHandling.FileDashboard;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static edu.ntnu.idatt2001.frontend.SceneSwitcher.primaryStage;

public class Pane1 extends StackPane {

    private static final List<ImageView> imageViews = new ArrayList<>();
    private static int selectedIndex = -1;


    public Pane1() {

        VBox structure = new VBox();
        structure.getStylesheets().add("/Style.css");
        VBox content = new VBox();
        File characters = new File("src/main/resources/characters/");
        String[] filenames = characters.list();
        ArrayList<String> names = new ArrayList<String>(Arrays.asList());

        for (String filename : filenames) {
            String name = filename.replaceFirst("[.][^.]+$", ""); // remove file extension
            names.add(name);
        }
        ComboBox<String> comboBox = new ComboBox<String>();
        comboBox.getItems().addAll(names);
        comboBox.setPromptText("Select character");
        comboBox.setId("comboBox");

        File paths = new File("src/main/resources/paths/");
        String[] filenames2 = paths.list();
        ArrayList<String> names2 = new ArrayList<String>(Arrays.asList());
        for (String filename : filenames2) {
            String name = filename.replaceFirst("[.][^.]+$", ""); // remove file extension
            names2.add(name);
        }
        ComboBox<String> comboBox2 = new ComboBox<String>();
        comboBox2.getItems().addAll(names2);
        comboBox2.setPromptText("Select path");
        comboBox2.setId("comboBox");

        Button playButton = new Button("Play");
        playButton.setId("navigationButton");
        playButton.setOnAction(event -> {

            System.out.println(processSelectedImage());
            if (comboBox2 != null) {
                // Handle the selected file here
                CreateGame game = new CreateGame("src/main/resources/paths/"+comboBox2.getValue()+".paths");
                PaneGenerator gui = null;
                try {
                    //processSelectedImage();
                    gui = new PaneGenerator(game.gameGenerator("src/main/resources/characters/"+comboBox.getValue()+".paths"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    gui.start(primaryStage);
                    primaryStage.setFullScreen(true);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
                    imageView.setFitHeight(150);
                    imageView.setOnMouseClicked(event -> selectImage(imageView));
                    imageViews.add(imageView);
                    fileNames.add(file.getName());
                }
            }
        }




        imageBox.getChildren().addAll(imageViews);


        Button openButton = new Button("Import game from desktop");
        openButton.setId("navigationButton");
        openButton.setOnAction(event -> {


            // Create a file chooser dialog
            FileChooser fileChooser = new FileChooser();

            // Set the initial directory to the user's desktop
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home"), "Desktop"));

            // Show the dialog and wait for the user to select a file
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                // Handle the selected file here
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                CreateGame game = new CreateGame(selectedFile.getAbsolutePath());
                PaneGenerator gui = null;
                try {
                    gui = new PaneGenerator(game.gameGenerator("src/main/resources/characters/"+comboBox.getValue()+".paths"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    gui.start(primaryStage);
                    primaryStage.setFullScreen(true);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        setStyle("-fx-background-color: #a9cade;");

        Button backButton = new Button("Back to Main");
        backButton.setOnAction(e -> {
                    SceneSwitcher.switchToMainMenu();
                    //getChildren().clear();
                    imageViews.clear();

                }
        );
        backButton.setAlignment(Pos.TOP_LEFT);

        content.getChildren().addAll(comboBox,comboBox2,imageBox,playButton, openButton);
        content.setAlignment(Pos.BASELINE_CENTER);
        content.setSpacing(20);

        structure.getChildren().addAll(backButton, content);
        structure.setSpacing(20);

        getChildren().addAll(structure);
    }


    private void selectImage(ImageView selectedImageView) {
        if (selectedIndex >= 0 && selectedIndex < imageViews.size()) {
            ImageView prevSelectedImageView = imageViews.get(selectedIndex);
            prevSelectedImageView.setEffect(null);
        }
        selectedIndex = imageViews.indexOf(selectedImageView);
        selectedImageView.setEffect(new javafx.scene.effect.InnerShadow(50, Color.BLUEVIOLET));
    }



    public static String processSelectedImage() {
        ImageView selectedImageView = imageViews.get(selectedIndex);
        String imageUrl = selectedImageView.getImage().getUrl();
        int index = imageUrl.lastIndexOf("/") + 1;
        return imageUrl.substring(index);
    }

}

