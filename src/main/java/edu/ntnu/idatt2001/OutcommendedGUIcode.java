package edu.ntnu.idatt2001;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OutcommendedGUIcode {
}

//
//    @Override
//    public void start(Stage primaryStage) {
//        this.primaryStage = primaryStage;
//
//        BorderPane root = new BorderPane();
//        Scene mainScene = new Scene(root, 800, 600);
//        primaryStage.setScene(mainScene);
//
//        // Read the content of the file
//        String[] panes = formattedStory.split("::");
//
//        for (String pane : panes) {
//            VBox paneVBox = new VBox();
//            root.setCenter(paneVBox);
//
//            // Split the pane into lines
//            String[] lines = pane.trim().split("\n");
//
//            for (String line : lines) {
//                Label label = new Label(line.trim());
//                label.setStyle("-fx-font-size: 20px");
//
//                // Check if the line contains a button
//                if (line.contains("[") && line.contains("]")) {
//                    int startIndex = line.indexOf("[");
//                    int endIndex = line.indexOf("]");
//
//                    String buttonText = line.substring(startIndex + 1, endIndex);
//                    Button button = new Button(buttonText);
//
//                    // Create a new scene when the button is clicked
//                    button.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event) {
//                            Scene targetScene = getScene(buttonText);
//                            primaryStage.setScene(targetScene);
//                        }
//                    });
//
//                    // Add the button to the label and the label to the pane VBox
//                    label.setGraphic(button);
//                }
//
//                paneVBox.getChildren().add(label);
//                paneVBox.setAlignment(Pos.CENTER);
//            }
//        }
//
//        primaryStage.show();
//    }