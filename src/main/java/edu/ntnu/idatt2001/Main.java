package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.GUI.PaneGenerator;
import edu.ntnu.idatt2001.fileHandling.CreateGame;
import edu.ntnu.idatt2001.frontend.MyGUI;
import javafx.application.Application;

import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        //edu.ntnu.idatt2001.GUI.StartingPage gui = new edu.ntnu.idatt2001.GUI.StartingPage();
        MyGUI gui = new MyGUI();
        CreateGame game = new CreateGame("src/main/resources/hauntedHouse.paths");
        //PaneGenerator gui = new PaneGenerator(game.gameGenerator("src/main/resources/characters/warrior.paths"));
        gui.start(stage);

    }

}