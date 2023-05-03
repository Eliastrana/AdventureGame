package edu.ntnu.idatt2001.frontend;


import edu.ntnu.idatt2001.GUI.PaneGenerator;
import edu.ntnu.idatt2001.GUI.PathsFileGUI;
import edu.ntnu.idatt2001.Game;
import edu.ntnu.idatt2001.fileHandling.CreateGame;
import edu.ntnu.idatt2001.fileHandling.FileDashboard;
import edu.ntnu.idatt2001.fileHandling.SaveFileReader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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

    public static TextField saveName = new TextField();



    public Pane1() throws IOException {

        VBox structure = new VBox();
        structure.getStylesheets().add("/Style.css");
        VBox content = new VBox();


        HBox savedGames = new HBox();
        savedGames.setAlignment(Pos.CENTER);
        savedGames.setSpacing(15);

        File savedGamesfolder = new File("src/main/resources/saveData/");
        File[] listOfFiles = savedGamesfolder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {

                StackPane pane = new StackPane();
                pane.setPadding(new Insets(10));
                pane.setPrefSize(120, 50);

                Rectangle background = new Rectangle();
                background.setId("savedGamePane");
                background.setWidth(120);
                background.setHeight(50);
                background.setArcWidth(20);
                background.setArcHeight(20);
                background.setFill(Color.rgb(255, 255, 255, 0.8));



                VBox savedGameContent = new VBox();
                Text savedGameText = new Text(SaveFileReader.fileParser(file.getPath()));
                savedGameContent.getChildren().add(savedGameText);

                content.setAlignment(Pos.CENTER);
                content.setSpacing(5);

                pane.setOnMouseClicked(e -> {

                    CreateGame game = new CreateGame("src/main/resources/paths/"+"hauntedHouse"+".paths");

                    //String playerStats = "Character: " + comboBox.getValue() + "\n" + "Path: " + comboBox2.getValue() + "\n";
                    try {
                        //FileDashboard.gameSave(playerStats, "src/main/resources/saveData/"+saveName.getText()+".txt");


                        PaneGenerator gui = null;
                        try {
                            //processSelectedImage();
                            gui = new PaneGenerator(game.gameGenerator("src/main/resources/characters/" + "warrior" + ".paths"));

                            gui.start(primaryStage);
                            primaryStage.setFullScreen(true);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);

                        }
                    } finally {

                    }


//                    CreateGame game = new CreateGame("src/main/resources/paths/hauntedHouse.paths");
//                    PaneGenerator gui = null;
//
//                    try {
//                        gui = new PaneGenerator(game.gameGenerator("src/main/resources/characters/warrior.paths"));
//                    } catch (IOException ex) {
//                        throw new RuntimeException(ex);
//                    }
//
//                    try {
//                        gui.start(primaryStage);
//                    } catch (IOException ex) {
//                        throw new RuntimeException(ex);
//                    }
//                    primaryStage.setFullScreen(true);
                });

                pane.getChildren().addAll(background, savedGameContent);
                savedGames.getChildren().add(pane);
            }
        }



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

        saveName.setPromptText("Enter save name");
        saveName.setId("textField");

        Button playButton = new Button("Play");
        playButton.setId("navigationButton");
        playButton.setOnAction(event -> {

            try {
                FileDashboard.gameSave(processSelectedImage(), "src/main/resources/saveData/"+saveName.getText()+".txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(processSelectedImage());
            if (comboBox2.getItems() != null) {
                // Handle the selected file here
                CreateGame game = new CreateGame("src/main/resources/paths/"+comboBox2.getValue()+".paths");

                String playerStats = "Character: " + comboBox.getValue() + "\n" + "Path: " + comboBox2.getValue() + "\n";

                try {
                    FileDashboard.gameSave(playerStats, "src/main/resources/saveData/"+saveName.getText()+".txt");

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                PaneGenerator gui = null;
                try {
                    //processSelectedImage();
                    gui = new PaneGenerator(game.gameGenerator("src/main/resources/characters/"+comboBox.getValue()+".paths"));
                    FileDashboard.gameSave(comboBox.getValue(), "src/main/resources/saveData/"+saveName.getText()+".txt");

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
                    imageView.setFitHeight(160);
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

        HBox comboBoxes = new HBox();
        comboBoxes.getChildren().addAll(comboBox, comboBox2);
        comboBoxes.setAlignment(Pos.CENTER);
        comboBoxes.setSpacing(20);

        HBox playButtons = new HBox();
        playButtons.getChildren().addAll(playButton, openButton);
        playButtons.setAlignment(Pos.CENTER);
        playButtons.setSpacing(20);

        content.getChildren().addAll(savedGames, comboBoxes,imageBox,saveName, playButtons);
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

