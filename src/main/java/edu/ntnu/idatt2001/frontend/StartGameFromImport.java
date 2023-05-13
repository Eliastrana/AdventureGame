package edu.ntnu.idatt2001.frontend;

import edu.ntnu.idatt2001.GUI.PaneGenerator;
import edu.ntnu.idatt2001.Game;
import edu.ntnu.idatt2001.Link;
import edu.ntnu.idatt2001.fileHandling.CreateGame;
import edu.ntnu.idatt2001.fileHandling.FileDashboard;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import static edu.ntnu.idatt2001.frontend.Pane1.*;
import static edu.ntnu.idatt2001.frontend.Pane1.processSelectedImage;
import static edu.ntnu.idatt2001.frontend.SceneSwitcher.primaryStage;

public class StartGameFromImport {

    public static void StartGameFromImportMethod() throws IOException {

        String saveData = "src/main/resources/saveData/" + saveName.getText();
        String characterFile = "src/main/resources/characters/" + comboBoxCharacter.getValue() + ".paths";
        String playerStats = comboBoxCharacter.getValue() + "\n"  + comboBoxPath.getValue() +"\n";

        FileDashboard.gameSave(processSelectedImage(), saveData);


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
            Game gameCreated = game.gameGenerator(characterFile);

            FileDashboard.gameSave(playerStats, saveData);

            if (gameCreated.getStory().getBrokenLinks().size() != 0) {
                Alert brokenLinks = new Alert(Alert.AlertType.WARNING);
                brokenLinks.setTitle("Warning");
                brokenLinks.setHeaderText("Broken links");
                brokenLinks.setContentText("Number of broken links: " + gameCreated.getStory().getBrokenLinks().size()
                        + "\n" + "The following links are broken: " + gameCreated.getStory().getBrokenLinks());

                ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                ButtonType removeButton = new ButtonType("Remove", ButtonBar.ButtonData.APPLY);

                brokenLinks.getButtonTypes().addAll(cancelButton, removeButton);

                Optional<ButtonType> result = brokenLinks.showAndWait();
                if (result.isPresent()) {
                    if (result.get() == cancelButton) {
                        return;
                    } else if (result.get() == removeButton) {
                        List<Link> brokenLinksList = gameCreated.getStory().getBrokenLinks();
                        for (Link link : brokenLinksList) {
                            gameCreated.getStory().removePassage(link);
                        }
                    }
                }
            }

            try {
                gui = new PaneGenerator(game.gameGenerator(characterFile));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                // Save a copy of the selected file to the imported-games directory
                String fileName = selectedFile.getName();
                File importDir = new File("src/main/resources/paths");
                if (!importDir.exists()) {
                    importDir.mkdir();
                }
                File destFile = new File(importDir, fileName);
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Start the game GUI with the generated game

                gui.start(primaryStage);
                primaryStage.setFullScreen(true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }


}
