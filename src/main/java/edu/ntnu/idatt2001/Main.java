package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.view.MasterPane;
import javafx.application.Application;

import javafx.stage.Stage;

/**
 * The main class of the application.
 */

public class Main extends Application {

    /**
     * The main method.
     *
     * @param args The arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start method.
     *
     * @param stage The stage
     * @throws Exception The exception
     */

    @Override
    public void start(Stage stage) throws Exception {

        MasterPane gui = new MasterPane();
        gui.start(stage);

    }

}