package edu.ntnu.idatt2001.fileHandling;

import edu.ntnu.idatt2001.Link;
import edu.ntnu.idatt2001.Passage;
import edu.ntnu.idatt2001.Story;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.Desktop;

public class FileHandlerGUI extends Application {

    private FileHandler fileHandler;
    private TextArea textArea;


    @Override
    public void start(Stage primaryStage) throws Exception {
        // Opprett en ny instans av FileHandler-klassen
        fileHandler = new FileHandler();
        textArea = new TextArea();


        // Opprett en FileChooser
        FileChooser fileChooser = new FileChooser();

        // Sett opp FileChooser til å åpne og lagre .paths-filer
        fileChooser.setTitle("Velg historiefil");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Paths-filer", "*.paths"));

        // Opprett knapper for å åpne, lagre og vise historie
        Button openButton = new Button("Åpne fil");
        Button saveButton = new Button("Lagre fil");
        Button historyButton = new Button("Historie");

        // Legg til hendelseslyttere for knappene
        openButton.setOnAction(event -> openFile(primaryStage, fileChooser));
        saveButton.setOnAction(event -> {
            try {
                saveFile(primaryStage, fileChooser);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        historyButton.setOnAction(event -> showHistory());

        // Legg til knappene til et VBox-pane
        VBox root = new VBox(openButton, saveButton, historyButton);

        // Vis scenen
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openFile(Stage primaryStage, FileChooser fileChooser) {
        // Show the file chooser dialog and let the user choose a file to open
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            // Read the file contents into a List<String>
            List<String> lines = fileHandler.readFile(file);

            // Join the lines using a newline separator to get a single String
            String content = String.join("\n", lines);

            // Create a new TextArea and display the file contents
            TextArea textArea = new TextArea(content);
            Stage stage = new Stage();
            stage.setTitle(file.getName());
            stage.setScene(new Scene(textArea, 400, 300));
            stage.show();
        }
    }



    private void saveFile(Stage primaryStage, FileChooser fileChooser) throws IOException {
        // Show the file chooser dialog and let the user choose a file to save to
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            // Get the lines from the textArea
            List<String> lines = Arrays.asList(textArea.getText().split("\n"));

            // Write the lines to the file
            fileHandler.writeFile(file, lines);
        }
    }






    private void showHistory() {
        // Get the history from the file handler
        List<String> history = fileHandler.getHistory();

        // Create a new TextArea to display the history
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);

        // Append each line of history to the TextArea
        for (String line : history) {
            textArea.appendText(line + "\n");
        }

        // Create a new Stage to display the TextArea
        Stage stage = new Stage();

        // Set the title of the stage and the scene
        stage.setTitle("Historie");
        Scene scene = new Scene(textArea, 400, 300);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}