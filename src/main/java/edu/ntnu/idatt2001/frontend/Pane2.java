package edu.ntnu.idatt2001.frontend;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Pane2 extends StackPane {

    public Pane2() {
        setStyle("-fx-background-color: #ccd5ff;");

        VBox structure = new VBox();


        Text text1 = new Text("Pane 2");

        VBox vBox = new VBox();
        Text hello = new Text("Hello");
        Text world = new Text("World");
        vBox.getChildren().addAll(hello, world);
        vBox.setAlignment(Pos.CENTER);

        text1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        Button backButton = new Button("Back to Main");
        backButton.setAlignment(Pos.TOP_LEFT);
        backButton.setOnAction(e -> SceneSwitcher.switchToMainMenu());

        structure.getChildren().addAll(text1, backButton, vBox);

        getChildren().addAll(structure);
    }
}

