package edu.ntnu.idatt2001.frontend;


import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Pane4 extends StackPane {

    public Pane4() {
        setStyle("-fx-background-color: #e01111;");

        Text text1 = new Text("Pane 4");
        text1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        Button backButton = new Button("Back");
        backButton.setId("backNavigation");

        backButton.setOnAction(e -> SceneSwitcher.switchToMainMenu());

        getChildren().addAll(text1, backButton);
    }
}

