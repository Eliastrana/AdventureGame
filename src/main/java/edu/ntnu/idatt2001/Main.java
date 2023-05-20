package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.view.MyGUI;
import javafx.application.Application;

import javafx.stage.Stage;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        MyGUI gui = new MyGUI();
        gui.start(stage);

    }

}