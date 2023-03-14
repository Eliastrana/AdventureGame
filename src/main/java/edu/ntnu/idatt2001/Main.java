package edu.ntnu.idatt2001;


import edu.ntnu.idatt2001.fileHandling.AlternativeGUI;
import javafx.application.Application;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AlternativeGUI gui = new AlternativeGUI();
        //GUI gui = new GUI();
        gui.start(stage);

    }

}