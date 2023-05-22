package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.view.MainMenuPane;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * A class that switches between scenes.
 */
public class SceneSwitcher {

  public static Stage primaryStage;
  public static Scene mainScene;
  private static StackPane stackPane;

  /**
   * Creates a new scene switcher with the given primaryStage and mainScene.
   *
   * @param primaryStage the primary stage
   * @param mainScene    the main scene
   * @throws IOException if an I/O error occurs
   */
  public SceneSwitcher(Stage primaryStage, Scene mainScene) throws IOException {
    SceneSwitcher.primaryStage = primaryStage;
    SceneSwitcher.mainScene = mainScene;
    stackPane = new StackPane();
    stackPane.setStyle("-fx-background-color: #ffffff;");
    stackPane.getChildren().add(new MainMenuPane());
  }

  /**
   * Switches to the given scene.
   *
   * @param pane to switch to
   */
  public static void switchToPane(StackPane pane) {
    stackPane.getChildren().add(pane);
    if (pane.getChildren().size() > 1) {
      Button backButton = (Button) pane.getChildren().get(1);
      backButton.setOnAction(e -> stackPane.getChildren().remove(pane));
    }
  }

  /**
   * Switches to the main menu.
   */
  public static void switchToMainMenu() {
    stackPane.getChildren().remove(1);
  }

  /**
   * Switches to the game pane.
   */

  public static StackPane getStackPane() {
    return stackPane;
  }
}
