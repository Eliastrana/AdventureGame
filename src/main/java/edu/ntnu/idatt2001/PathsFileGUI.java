package edu.ntnu.idatt2001;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PathsFileGUI {

    private final String formattedStory;

    private Stage primaryStage;


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
                            Scene targetScene = getScene(buttonText);
                            primaryStage.setScene(targetScene);
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



    private Scene getScene(String target) {
        VBox root = new VBox();
        Scene scene = new Scene(root, 1000, 700);

        // Iterate over each pane
        for (String pane : formattedStory.split("::")) {
            // Split the pane into lines
            String[] lines = pane.trim().split("\n");

            // Check if the title of the pane matches the target
            if (lines[0].contains("(") && lines[0].contains(")")) {
                int startIndex = lines[0].indexOf("(");
                int endIndex = lines[0].indexOf(")");

                String title = lines[0].substring(startIndex + 1, endIndex);

                if (title.equals(target)) {
                    VBox paneVBox = new VBox();

                    for (String line : lines) {
                        Label label = new Label(line.trim());
                        label.setStyle("-fx-font-size: 20px");

                        if (line.contains("[") && line.contains("]")) {
                            int buttonStartIndex = line.indexOf("[");
                            int buttonEndIndex = line.indexOf("]");

                            String buttonText = line.substring(buttonStartIndex + 1, buttonEndIndex);

                            Button button = new Button(buttonText);

                            // Create a new scene when the button is clicked
                            button.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    Scene targetScene = getScene(getTarget(line));
                                    primaryStage.setScene(targetScene);
                                }
                            });

                            // Add the button to the label and the label to the pane VBox
                            label.setGraphic(button);
                        }

                        paneVBox.getChildren().add(label);
                        paneVBox.setAlignment(Pos.CENTER);
                    }

                    root.getChildren().clear();
                    root.getChildren().add(paneVBox);
                    break;
                }
            }
        }

        System.out.println("Target: " + target);
        return scene;
    }


    private String getNextTitle(String pane) {
        // Split the pane into lines
        String[] lines = pane.trim().split("\n");

        // Check if the title of the pane matches the target
        if (lines[0].contains("(") && lines[0].contains(")")) {
            int startIndex = lines[0].indexOf("(");
            int endIndex = lines[0].indexOf(")");

            String title = lines[0].substring(startIndex + 1, endIndex);

            // Find the index of the current pane
            int currentIndex = formattedStory.indexOf(pane);

            // Find the next pane in the story
            int nextIndex = formattedStory.indexOf("::", currentIndex + pane.length());

            if (nextIndex == -1) {
                // This is the last pane in the story
                return null;
            } else {
                // Get the next pane and its title
                String nextPane = formattedStory.substring(nextIndex + 2);
                String[] nextPaneLines = nextPane.trim().split("\n");
                String nextTitle = nextPaneLines[0].substring(startIndex + 1, endIndex);

                System.out.println("Next Pane: " + nextPane);
                return nextTitle;
            }
        }

        return null;
    }



    private String getTarget(String line) {
        int startIndex = line.indexOf("[");
        int endIndex = line.indexOf("]");

        return line.substring(startIndex + 1, endIndex).trim();
    }


}





