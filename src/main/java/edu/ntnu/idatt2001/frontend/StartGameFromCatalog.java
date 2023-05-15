package edu.ntnu.idatt2001.frontend;

import edu.ntnu.idatt2001.GUI.PaneGenerator;
import edu.ntnu.idatt2001.Game;
import edu.ntnu.idatt2001.Link;
import edu.ntnu.idatt2001.fileHandling.CreateGame;
import edu.ntnu.idatt2001.fileHandling.FileDashboard;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static edu.ntnu.idatt2001.frontend.Pane1.processSelectedImage;
import static edu.ntnu.idatt2001.frontend.Pane1.saveName;
import static edu.ntnu.idatt2001.frontend.SceneSwitcher.primaryStage;
import static edu.ntnu.idatt2001.frontend.Pane1.comboBoxCharacter;
import static edu.ntnu.idatt2001.frontend.Pane1.comboBoxPath;

public class StartGameFromCatalog {

    public static void startGameFromCatalogMethod() throws IOException {

        String saveData = "src/main/resources/saveData/" + saveName.getText();
        String pathFile = "src/main/resources/paths/" + comboBoxPath.getValue() + ".paths";
        String characterFile = "src/main/resources/characters/" + comboBoxCharacter.getValue() + ".paths";
        String playerStats = comboBoxCharacter.getValue() + "\n"  + comboBoxPath.getValue() +"\n";
        String goals = "src/main/resources/goals/" + comboBoxPath.getValue();


        FileDashboard.gameSave(processSelectedImage(), saveData);
        System.out.println(processSelectedImage());

        if (comboBoxPath.getItems() != null) {
            // Handle the selected file here
            CreateGame game = new CreateGame(pathFile);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            FileDashboard.gameSave(playerStats, saveData);

            PaneGenerator gui;
            Game gameCreated = game.gameGenerator(characterFile);

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

            gui = new PaneGenerator(gameCreated);

            gui.start(primaryStage);
            primaryStage.setFullScreen(true);


        }
}



}
