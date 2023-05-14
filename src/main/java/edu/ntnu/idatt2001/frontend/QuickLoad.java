package edu.ntnu.idatt2001.frontend;

import edu.ntnu.idatt2001.fileHandling.SaveFileReader;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class QuickLoad {

    public static HBox savedGamesContainer() throws IOException {


        HBox savedGames = new HBox();
        savedGames.setAlignment(Pos.CENTER);
        savedGames.setSpacing(15);

        File savedGamesfolder = new File("src/main/resources/saveData/");
        File[] listOfFiles = savedGamesfolder.listFiles();

        Arrays.sort(listOfFiles, Comparator.comparingLong(File::lastModified).reversed());

        int count = 0;
        for (File file : listOfFiles) {
            if (file.isFile() && count < 3) { // Only process the first three files
                StackPane pane = new StackPane();
                pane.setId("savedGamePane");

                Rectangle background = new Rectangle();
                background.setId("savedGamePane");


                HBox savedGameContentStructure = new HBox();

                VBox savedGameContent = new VBox();
                Text savedGameCharacter = new Text("Character name: "+"\n"+SaveFileReader.getName(file.getPath()));
                savedGameCharacter.setStyle("-fx-fill: #ffffff; -fx-font-size: 10");

                savedGameCharacter.setId("savedGameText");

                Text savedGamePath = new Text("Chosen path: "+"\n"+SaveFileReader.getPath(file.getPath()));
                savedGamePath.setId("savedGameText");
                savedGamePath.setStyle("-fx-fill: #ffffff; -fx-font-size: 10");

                Text savedGameCurrentPassage = new Text("Last seen passage: "+"\n"+SaveFileReader.getLastSeenPassage(file.getPath()));
                savedGameCurrentPassage.setId("savedGameText");
                savedGameCurrentPassage.setStyle("-fx-fill: #ffffff; -fx-font-size: 10");


                Image image = new Image("file:src/main/resources/characterIcons/" +SaveFileReader.getImage(file.getPath()));
                System.out.println("file:src/main/resources/characterIcons/" +SaveFileReader.getImage(file.getPath()));
                ImageView savedGameImage = new ImageView(image);
                savedGameImage.setFitHeight(100);
                savedGameImage.setFitWidth(60);
                savedGameImage.setPreserveRatio(true);
                savedGameContent.setAlignment(Pos.BASELINE_RIGHT);
                savedGameContent.getChildren().addAll(savedGameCharacter, savedGamePath, savedGameCurrentPassage);

                savedGameContentStructure.getChildren().addAll(savedGameContent, savedGameImage);
                pane.getChildren().addAll(savedGameContentStructure);



                pane.setOnMouseClicked(e -> {
                    // Write code to reload game
                    System.out.println("Clicked on " + file.getName());



                });

                pane.getChildren().addAll(background, savedGameContent);
                savedGames.getChildren().add(pane);

                count++;

            }
        }

        return savedGames;

    }
}
