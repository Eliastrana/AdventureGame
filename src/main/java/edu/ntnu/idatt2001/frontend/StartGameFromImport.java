package edu.ntnu.idatt2001.frontend;

import edu.ntnu.idatt2001.GUI.PaneGenerator;
import edu.ntnu.idatt2001.fileHandling.CreateGame;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

import static edu.ntnu.idatt2001.frontend.Pane1.comboBoxPath;
import static edu.ntnu.idatt2001.frontend.SceneSwitcher.primaryStage;

public class StartGameFromImport {

    public static void StartGameFromImportMethod(){

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
                gui = new PaneGenerator(game.gameGenerator("src/main/resources/characters/"+comboBoxPath.getValue()+".paths"));
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
    }
}
