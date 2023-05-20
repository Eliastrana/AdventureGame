package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.view.MasterPane;
import javafx.application.Application;

import javafx.stage.Stage;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        MasterPane gui = new MasterPane();
        gui.start(stage);

    }

}