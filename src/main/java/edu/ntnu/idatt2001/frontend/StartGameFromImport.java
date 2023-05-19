package edu.ntnu.idatt2001.frontend;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;

import static edu.ntnu.idatt2001.frontend.Pane1.comboBoxPath;
import static edu.ntnu.idatt2001.frontend.SceneSwitcher.primaryStage;

public class StartGameFromImport {

    public static void startGameFromImportMethod() throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home"), "Desktop"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Paths Files", "*.paths"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            String fileName = selectedFile.getName();
            File importDir = new File("src/main/resources/paths");
            if (!importDir.exists()) {
                importDir.mkdir();
            }
            File destFile = new File(importDir, fileName);
            Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            String path = destFile.getAbsolutePath().replace("\\", "/");
            String[] tokens = path.split("/");
            String newPath = tokens[tokens.length - 1];

            comboBoxPath.getItems().clear();

            File paths = new File("src/main/resources/paths/");
            String[] filenames2 = paths.list();
            ArrayList<String> names2 = new ArrayList<>(Arrays.asList());
            for (String filename : filenames2) {
                if (filename.endsWith(".paths")) {
                    String name = filename.replaceFirst("[.][^.]+$", ""); // remove file extension
                    names2.add(name);
                }
            }

            comboBoxPath.getItems().addAll(names2);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getDialogPane().setId("alertBox");
            alert.setTitle("File imported successfully");
            alert.setHeaderText("The file was added to the paths folder.");
            alert.setContentText("The file name is " + newPath);
            alert.showAndWait();
        }
    }
}
