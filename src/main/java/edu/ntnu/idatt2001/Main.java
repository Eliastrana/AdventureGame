package edu.ntnu.idatt2001;


import javafx.application.Application;
import javafx.stage.Stage;

import java.nio.file.Path;

import static javafx.application.Application.launch;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        PathsFileGUI gui = new PathsFileGUI();
        //GUI gui = new GUI();
        gui.start(stage);

    }

}