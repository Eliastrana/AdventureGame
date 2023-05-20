package edu.ntnu.idatt2001.view;

import java.io.IOException;

import edu.ntnu.idatt2001.controller.SceneSwitcher;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class MasterPane extends Application {

  private Scene mainScene;
  private SceneSwitcher sceneSwitcher;

  @Override
  public void start(Stage primaryStage) throws IOException {
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
  }

  public static void main(String[] args) {
    launch(args);
  }
}
