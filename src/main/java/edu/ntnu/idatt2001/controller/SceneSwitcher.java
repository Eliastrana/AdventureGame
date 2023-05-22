package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.view.MainMenuPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    public static Stage primaryStage;
    public static Scene mainScene;
    private static StackPane stackPane;

    public SceneSwitcher(Stage primaryStage, Scene mainScene) throws IOException {
        this.primaryStage = primaryStage;
        this.mainScene = mainScene;
        stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color: #ffffff;");
        stackPane.getChildren().add(new MainMenuPane());
    }

    public static void switchToPane(StackPane pane) {
        stackPane.getChildren().add(pane);
        if (pane.getChildren().size() > 1) {
            Button backButton = (Button) pane.getChildren().get(1);
            backButton.setOnAction(e -> stackPane.getChildren().remove(pane));
        }
    }

//    public static void switchToPane1() throws IOException {
//        stackPane.getChildren().clear();
//        primaryStage.setFullScreen(false);
//        primaryStage.hide();
//        Pane1 pane1 = new Pane1();
//        pane1.setStyle("-fx-background-color: #a9cade;");
//        primaryStage.setScene(new Scene(pane1));
//        primaryStage.show();
//    }



    public static void switchToMainMenu() {
        stackPane.getChildren().remove(1);
    }

    public static StackPane getStackPane() {
        return stackPane;
    }



//    public static void quitFullScreen() throws IOException {
//        primaryStage.setFullScreen(false);
//        stackPane.getChildren().remove(1);
//        stackPane.getChildren().add(new MainMenuPane());
//    }
//



}
