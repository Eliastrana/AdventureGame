package edu.ntnu.idatt2001.frontend;


import edu.ntnu.idatt2001.utility.SoundPlayer;
import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;



public class MainMenuPane extends VBox {

  public MainMenuPane() throws IOException {


    SoundPlayer.playOnLoop("src/main/resources/sounds/ambiance.wav");
    setStyle("-fx-background-image: url('mainmenubackgroundsmall.jpeg')");

    VBox structure = new VBox();
    structure.getStylesheets().add("/Style.css");
    structure.setAlignment(Pos.BOTTOM_LEFT);
    structure.setSpacing(15);

    Text titleText = new Text("Adventure Game");
    titleText.setId("title");

    HBox menuButtons = new HBox();
    menuButtons.setAlignment(Pos.CENTER);

    menuButtons.setSpacing(15);
    Button button1 = new Button("Load Game");
    button1.setId("mainMenuButton");
    Button button2 = new Button("Create Game");
    button2.setId("mainMenuButton");
    Button button3 = new Button("Create Character");
    button3.setId("mainMenuButton");
    Button button4 = new Button("Goals");
    button4.setId("mainMenuButton");
    menuButtons.getChildren().addAll(button1, button2, button3, button4);

    HBox hBox = QuickLoad.savedNameDisplayer("characterPath",
            "filePath",
            "goalsPath",
            "characterIcon");
    structure.getChildren().addAll(titleText, menuButtons, hBox);
    structure.setAlignment(Pos.CENTER);
    getChildren().addAll(structure);

    setAlignment(Pos.CENTER);


    button1.setOnAction(e -> {
      try {
        SceneSwitcher.switchToPane(new Pane1());
        SoundPlayer.play("src/main/resources/sounds/click.wav");
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });
    button2.setOnAction(e -> {
      SceneSwitcher.switchToPane(new Pane2());
      SoundPlayer.play("src/main/resources/sounds/click.wav");
    });
    button3.setOnAction(e -> {
      SceneSwitcher.switchToPane(new Pane3());
      SoundPlayer.play("src/main/resources/sounds/click.wav");
    });
    button4.setOnAction(e -> {
      SceneSwitcher.switchToPane(new Pane4());
      SoundPlayer.play("src/main/resources/sounds/click.wav");
    });


  }
}
