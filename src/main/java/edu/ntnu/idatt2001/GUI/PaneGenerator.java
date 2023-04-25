package edu.ntnu.idatt2001.GUI;

import edu.ntnu.idatt2001.Game;
import edu.ntnu.idatt2001.Link;
import edu.ntnu.idatt2001.Passage;
import edu.ntnu.idatt2001.fileHandling.CreateGame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class PaneGenerator extends Application {

    private Game game;
    private Label titleLabel;
    private Text contentArea;
    private VBox buttonBox;

    public PaneGenerator(Game game) {
        this.game = game;
    }

    @Override
    public void start(Stage stage) throws IOException {
        titleLabel = new Label(game.begin().getTitle());
        titleLabel.setStyle("-fx-font-size: 24px; -fx-alignment: center;");

        contentArea = new Text();
        buttonBox = new VBox();
        buttonBox.setSpacing(10);

        updateContentAndButtons(game.begin());

        BorderPane root = new BorderPane();
        root.setTop(titleLabel);
        root.setCenter(contentArea);
        root.setBottom(buttonBox);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle(game.getStory().getTitle());
        stage.show();
    }

    private void updateContentAndButtons(Passage passage) {
        if (passage == null) {
            System.out.println("Error: Passage not found.");
            return;
        }

        titleLabel.setText(passage.getTitle());
        contentArea.setText(passage.getContent());

        buttonBox.getChildren().clear();
        List<Link> links = passage.getLinks();
        for (Link link : links) {
            Button button = new Button(link.getText());
            button.setOnAction(event -> {
                Passage nextPassage = game.go(link);
                updateContentAndButtons(nextPassage);
            });

            buttonBox.getChildren().add(button);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
