package edu.ntnu.idatt2001;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PathsFileGUI extends Application {

    private static final String FILE_PATH = "src/main/resources/pickFile.paths";

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length != 2) {
                    continue;
                }
                String title = parts[0];
                String content = parts[1];

                Label titleLabel = new Label(title);
                titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

                VBox pane = new VBox();
                pane.setSpacing(5);
                pane.setPadding(new Insets(5));
                pane.getChildren().add(titleLabel);

                String[] contentParts = content.split("\\[");
                for (String part : contentParts) {
                    if (part.startsWith("]")) {
                        String[] buttonParts = part.substring(1).split("\\(");
                        String buttonText = buttonParts[0];
                        String buttonTarget = buttonParts[1].substring(0, buttonParts[1].length() - 1);

                        Button button = new Button(buttonText);
                        button.setOnAction(new ButtonClickHandler(buttonTarget));
                        pane.getChildren().add(button);
                    } else {
                        Label label = new Label(part);
                        pane.getChildren().add(label);
                    }
                }

                root.getChildren().add(pane);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private class ButtonClickHandler implements EventHandler<ActionEvent> {



        private String targetPane;

        public ButtonClickHandler(String targetPane) {
            this.targetPane = targetPane;
        }

        @Override
        public void handle(ActionEvent event) {
            VBox currentPane = (VBox) ((Button) event.getSource()).getParent();
            Scene scene = currentPane.getScene();
            VBox root = (VBox) scene.getRoot();

            for (Node child : root.getChildren()) {
                if (child instanceof VBox) {
                    PaneInfo paneInfo = (PaneInfo) child.getUserData();

                    if (paneInfo != null && paneInfo.getTitle().equals(targetPane)) {
                        scene.setRoot((VBox) child);
                        break;
                    }
                }
            }
        }

    }


}
