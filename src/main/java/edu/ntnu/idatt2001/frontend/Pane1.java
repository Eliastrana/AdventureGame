package edu.ntnu.idatt2001.frontend;


import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Pane1 extends StackPane {

    public Pane1() {

        setStyle("-fx-background-color: #ffcccc;");

        Text text1 = new Text("Pane 1");
        text1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        Button backButton = new Button("Back to Main");
        backButton.setOnAction(e -> SceneSwitcher.switchToMainMenu());

        getChildren().addAll(text1, backButton);
    }
}

