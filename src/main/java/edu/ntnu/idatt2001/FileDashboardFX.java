package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.fileHandling.FileWrite;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FileDashboardFX extends Application {
    @Override
    public void start(Stage primaryStage) {
        // create the main window
        VBox root = new VBox();
        Scene mainScene = new Scene(root, 300, 200);
        primaryStage.setScene(mainScene);

        // create the buttons for the main window
        Button writeButton = new Button("Write to file");
        Button readButton = new Button("Read from file");
        Button exitButton = new Button("Exit");
        root.getChildren().addAll(writeButton, readButton, exitButton);

        // set the actions for the main window buttons
        writeButton.setOnAction(e -> {
            // create a new scene for writing to a file
            VBox writeRoot = new VBox();
            Scene writeScene = new Scene(writeRoot, 300, 200);

            // create the input fields and buttons for writing to a file
            TextField filenameField = new TextField();
            Button addPassagesButton = new Button("Add passages");
            VBox passagesBox = new VBox();
            Button writeToFileButton = new Button("Write to file");
            writeRoot.getChildren().addAll(filenameField, addPassagesButton, passagesBox, writeToFileButton);

            // set the action for the add passages button
            addPassagesButton.setOnAction(e2 -> {
                // create new text fields for the passage and content inputs
                TextField passageField = new TextField();
                TextField contentField = new TextField();
                passagesBox.getChildren().addAll(passageField, contentField);
            });

            // set the action for the write to file button
            writeToFileButton.setOnAction(e2 -> {
                // get the passages from the text fields
                List<String> passages = new ArrayList<>();
                for (Node node : passagesBox.getChildren()) {
                    if (node instanceof TextField) {
                        passages.add(((TextField) node).getText());
                    }
                }

                // write the passages to the file
                try {
                    FileWrite writer = new FileWrite("src/main/resources/" + filenameField.getText() + ".paths");
                    writer.appendPathsFile(passages.toArray(new String[0]));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                // go back to the main window
                primaryStage.setScene(mainScene);
            });

            // switch to the write scene
            primaryStage.setScene(writeScene);
        });


    }

}
//        readButton.setOnAction(e -> {
//            // create a new scene
//            VBox readRoot = new VBox();
//            Scene readScene = new Scene(readRoot, 300, 200);
//
//