package edu.ntnu.idatt2001.frontend;

import edu.ntnu.idatt2001.GUI.PaneGenerator;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SceneSwitcher {

    public static Stage primaryStage;
    public static Scene mainScene;
    private static StackPane stackPane;

    public SceneSwitcher(Stage primaryStage, Scene mainScene) {
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

    public static void switchToPane1() {
        primaryStage.setFullScreen(false);
        MainMenuPane mainMenuPane = new MainMenuPane();
        mainMenuPane.getChildren().clear();
        mainMenuPane.getChildren().add(new Pane1());
        //Pane1 pane1 = new Pane1();
        primaryStage.getScene().setRoot(mainMenuPane);
    }



    public static void switchToMainMenu() {
        stackPane.getChildren().remove(1);
    }

    public static StackPane getStackPane() {
        return stackPane;
    }


}
