package edu.ntnu.idatt2001.fileHandling;

import edu.ntnu.idatt2001.Passage;
import edu.ntnu.idatt2001.Story;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AlternativeGUI extends Application {

    private static final String STORY_FILE = "/Users/eliastrana/Documents/Systemutvikling/Programmering2/Adventuregame/src/main/resources/pathExamples/test2";
    private static final String FILE_EXTENSION = ".paths";

    @Override
    public void start(Stage primaryStage) {

        // Load story from file
        Story story = StoryFileManager.loadFromFile(STORY_FILE); //den knekker når man legger til .paths

        // Generate buttons and titles
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        int rowIndex = 0;
        for (Passage passage : story.getPassages()) {
            // Create button for each passage
            Button button = new Button(passage.getTitle());
            Label label = new Label("Hello World");

            button.setOnAction(event -> {
                // Handle button click event
                // Replace the label text with passage content

                label.setText(passage.getContent());
            });
            // Add button to grid pane
            gridPane.add(button, 0, rowIndex);
            rowIndex++;

            // Add title label for each passage
            Label titleLabel = new Label(passage.getTitle());
            titleLabel.setStyle("-fx-font-weight: bold;");
            gridPane.add(titleLabel, 1, rowIndex - 1);
        }

        // Add label to display passage content
        Label label = new Label("Select a passage to display its content.");
        label.setPadding(new Insets(10));
        gridPane.add(label, 1, rowIndex);

        // Create scene and show stage
        Scene scene = new Scene(gridPane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

