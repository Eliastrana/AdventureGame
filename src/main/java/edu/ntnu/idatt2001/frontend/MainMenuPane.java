package edu.ntnu.idatt2001.frontend;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainMenuPane extends VBox {

    public MainMenuPane() {

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
        Button button4 = new Button("Settings");
        button4.setId("mainMenuButton");
        menuButtons.getChildren().addAll(button1, button2, button3, button4);

        structure.getChildren().addAll(titleText, menuButtons);
        structure.setAlignment(Pos.CENTER);
        getChildren().addAll(structure);

        setAlignment(Pos.CENTER);



        button1.setOnAction(e -> SceneSwitcher.switchToPane(new Pane1()));
        button2.setOnAction(e -> SceneSwitcher.switchToPane(new Pane2()));
        button3.setOnAction(e -> SceneSwitcher.switchToPane(new Pane3()));
        button4.setOnAction(e -> SceneSwitcher.switchToPane(new Pane4()));
    }
}
