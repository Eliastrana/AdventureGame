package edu.ntnu.idatt2001.frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MyGUI extends Application {

    private Scene mainScene;
    private SceneSwitcher sceneSwitcher;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Adventure Game");

        primaryStage.setMinHeight(540);
        primaryStage.setMinWidth(720);
        // Create main scene with four buttons
        MainMenuPane mainMenuPane = new MainMenuPane();
        mainScene = new Scene(mainMenuPane, 540, 720);
        mainMenuPane.setMinSize(540, 720);

        mainMenuPane.getStylesheets().add("/Style.css");


        // Create scene switcher to switch between scenes
        sceneSwitcher = new SceneSwitcher(primaryStage, mainScene);

        // Set main scene as root pane
        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(sceneSwitcher.getStackPane());

        // Set scene and show stage
        primaryStage.setScene(new Scene(rootPane, 400, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
