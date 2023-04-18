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


        System.out.println("Starting PaneGenerator...");
        titleLabel = new Label(game.begin().getTitle());
        titleLabel.setStyle("-fx-font-size: 24px; -fx-alignment: center;");
        System.out.println("Title label created.");

        contentArea = new Text();
        contentArea.setText(game.begin().getContent());

        buttonBox = new VBox();
        buttonBox.setSpacing(10);

        List<Link> links = this.game.begin().getLinks();
        System.out.println("Links:");
        for (Link link : links) {
            System.out.println(link.getText());
            Button button = new Button(link.getText());
            button.setOnAction(event -> {
                System.out.println(this.game.begin().getContent());
                System.out.println(link);
                Passage passage = game.go(link);
                System.out.println(passage);
                System.out.println("Passage: " +passage);
                contentArea.setText(passage.getContent()); //This is where the program breaks
                stage.setScene(new Scene(new BorderPane(contentArea, null, null, buttonBox, null)));
                stage.setTitle(passage.getTitle());
                stage.show();
            });

            buttonBox.getChildren().add(button);
        }

        BorderPane root = new BorderPane();
        root.setTop(titleLabel);
        root.setCenter(contentArea);
        root.setBottom(buttonBox);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle(game.getStory().getTitle());
        stage.show();

        System.out.println("PaneGenerator started.");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
