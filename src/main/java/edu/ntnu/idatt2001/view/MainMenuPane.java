package edu.ntnu.idatt2001.view;


import edu.ntnu.idatt2001.controller.QuickLoad;
import edu.ntnu.idatt2001.controller.SceneSwitcher;
import edu.ntnu.idatt2001.utility.AlertUtil;
import edu.ntnu.idatt2001.utility.SoundPlayer;
import java.io.IOException;

import edu.ntnu.idatt2001.utility.exceptions.InvalidFormatException;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;



/**
 * A class that represents the main menu pane.
 */
public class MainMenuPane extends VBox {

  String clickSound = "src/main/resources/sounds/click.wav";


  /**
   * Constructor for the main menu pane.
   * Displays the title and the buttons,
   * as well as the quickLoad panes if there are any
   */
  public MainMenuPane() {

    try {
      SoundPlayer.playOnLoop("src/main/resources/sounds/ambiance.wav");
    } catch (Exception e) {
      AlertUtil.showAlertBoxError("Audio error","Error playing sound" , e.getMessage());
    }
    setStyle("-fx-background-image: url('mainmenubackgroundsmall.jpeg')");

    VBox structure = new VBox();
    structure.getStylesheets().add("/Style.css");
    structure.setAlignment(Pos.BOTTOM_LEFT);
    structure.setSpacing(15);

    Text titleText = new Text("Adventure Game");
    titleText.setId("title");

    HBox menuButtons = new HBox();
    menuButtons.setAlignment(Pos.CENTER);

    String mainMenuButton = "mainMenuButton";

    menuButtons.setSpacing(15);
    Button button1 = new Button("Load Game");
    button1.setId(mainMenuButton);
    Button button2 = new Button("Create Game");
    button2.setId(mainMenuButton);
    Button button3 = new Button("Create Character");
    button3.setId(mainMenuButton);
    Button button4 = new Button("Goals");
    button4.setId(mainMenuButton);
    menuButtons.getChildren().addAll(button1, button2, button3, button4);

    try {
      HBox hBox = QuickLoad.savedNameDisplayer("characterPath",
              "filePath",
              "goalsPath",
              "characterIcon");
      structure.getChildren().addAll(titleText, menuButtons, hBox);
      structure.setAlignment(Pos.CENTER);
      getChildren().addAll(structure);
      setAlignment(Pos.CENTER);
    } catch (IOException | InvalidFormatException e) {
      AlertUtil.showAlertBoxError("Error","Error displaying name" , e.getMessage());
    }


    button1.setOnAction(e -> {
      try {
        SceneSwitcher.switchToPane(new LoadGamePane());
        SoundPlayer.play(clickSound);
      } catch (IOException ex) {
        AlertUtil.showAlertBoxError("Error","Error playing sound","No sound:(");
      }
    });
    button2.setOnAction(e -> {
      SceneSwitcher.switchToPane(new CreateGamePane());
      SoundPlayer.play(clickSound);
    });
    button3.setOnAction(e -> {
      SceneSwitcher.switchToPane(new CreateCharacterPane());
      SoundPlayer.play(clickSound);
    });
    button4.setOnAction(e -> {
      SceneSwitcher.switchToPane(new CreateGoalsPane());
      SoundPlayer.play(clickSound);
    });


  }
}
