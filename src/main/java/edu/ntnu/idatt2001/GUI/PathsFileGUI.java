package edu.ntnu.idatt2001.GUI;

import edu.ntnu.idatt2001.fileHandling.PlayerRegister;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class PathsFileGUI {

    private final String formattedStory;

    private Stage primaryStage;

    private TextField importCharacterField;


    public PathsFileGUI(String formattedStory) {
        this.formattedStory = formattedStory;
    }


    public void VBox(Stage primaryStage) {
        this.primaryStage = primaryStage;

        BorderPane root = new BorderPane();
        Scene mainScene = new Scene(root, 800, 600);
        primaryStage.setScene(mainScene);

        // Read the content of the file
        String[] panes = formattedStory.split("::");

        for (String pane : panes) {
            VBox paneVBox = new VBox();

            // Split the pane into lines
            String[] lines = pane.trim().split("\n");

            String title = lines[0].trim();

            for (String line : lines) {
                Label label = new Label(line.trim());
                label.setStyle("-fx-font-size: 20px");

                // Check if the line contains a button
                if (line.contains("[") && line.contains("]")) {
                    int startIndex = line.indexOf("[");
                    int endIndex = line.indexOf("]");

                    String buttonText = line.substring(startIndex + 1, endIndex);
                    Button button = new Button(buttonText);

                    // Create a new scene when the button is clicked
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            setTextOfVBoxPane(paneVBox, buttonText);
                        }
                    });

                    // Add the button to the label and the label to the pane VBox
                    label.setGraphic(button);
                }

                paneVBox.getChildren().add(label);
                paneVBox.setAlignment(Pos.CENTER);
            }

            // Add the pane VBox to the root BorderPane
            root.setCenter(paneVBox);

            // Set the title of the main scene to the title of the first pane
            if (title.equals("Welcome")) {
                primaryStage.setTitle(title);
                break;
            }
        }

        primaryStage.show();
    }

    private void setTextOfVBoxPane(VBox paneVBox, String target) {
        // Iterate over each label in the VBox
        for (Node node : paneVBox.getChildren()) {
            if (node instanceof Label) {
                Label label = (Label) node;
                String line = label.getText();

                // Check if the line contains a button
                if (line.contains("[") && line.contains("]")) {
                    int startIndex = line.indexOf("[");
                    int endIndex = line.indexOf("]");

                    String buttonText = line.substring(startIndex + 1, endIndex);

                    if (buttonText.equals(target)) {
                        // Get the text after the button text
                        String newText = line.substring(endIndex + 1).trim();
                        label.setText(newText);
                    }
                }
            }
        }
    }
}


