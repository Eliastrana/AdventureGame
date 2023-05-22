package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.controller.SceneSwitcher;
import java.io.IOException;

import edu.ntnu.idatt2001.utility.AlertUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * The main class of the application.
 * Opens the main menu pane
 */
public class MasterPane extends Application {

  private Scene mainScene;
  private SceneSwitcher sceneSwitcher;

  /**
   * Starts the application.
   *
   * @param primaryStage the primary stage
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void start(Stage primaryStage) throws IOException {

    try {
      primaryStage.setTitle("Adventure Game");

      primaryStage.setMinHeight(540);
      primaryStage.setMinWidth(720);

      MainMenuPane mainMenuPane = new MainMenuPane();
      mainScene = new Scene(mainMenuPane, 540, 720);
      mainMenuPane.setMinSize(540, 720);
      mainMenuPane.getStylesheets().add("/Style.css");

      sceneSwitcher = new SceneSwitcher(primaryStage, mainScene);

      BorderPane rootPane = new BorderPane();
      rootPane.setCenter(sceneSwitcher.getStackPane());
      primaryStage.setScene(new Scene(rootPane, 400, 400));
      primaryStage.show();
    } catch (IOException e) {
      AlertUtil.showAlertBoxError("Error",
              "An error occurred while loading the game.",
              e.getMessage());
    }

  }

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}
