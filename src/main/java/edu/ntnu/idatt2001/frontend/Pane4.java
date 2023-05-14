package edu.ntnu.idatt2001.frontend;

import edu.ntnu.idatt2001.utility.SoundPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.sound.midi.Soundbank;

public class Pane4 extends StackPane {

    TextField scoreField = new TextField();
    TextField goldField = new TextField();
    TextField healthField = new TextField();
    TextField inventoryField = new TextField();

    String scoreGoal = scoreField.getText();
    String goldGoal = goldField.getText();
    String healthGoal = healthField.getText();
    String inventoryGoal = inventoryField.getText();

    public Pane4() {

        setStyle("-fx-background-image: url('mainmenubackgroundsmall.jpeg')");

        VBox structure = new VBox();
        structure.getStylesheets().add("/Style.css");
        structure.setSpacing(20);
        structure.setPadding(new Insets(20, 20, 20, 20));

        Pane goalsPane = new Pane();
        goalsPane.setId("pane");
        goalsPane.setPadding(new Insets(20));
        goalsPane.setPrefSize(600, 500);


        Text title = new Text("Goals");
        title.setId("title");

        HBox backButtonHolder = new HBox();
        Button backButton = new Button("Back");
        backButton.setId("backNavigation");
        backButton.setAlignment(Pos.TOP_LEFT);
        backButtonHolder.setAlignment(Pos.TOP_LEFT);
        backButton.setOnAction(e -> SceneSwitcher.switchToMainMenu());

        HBox goals = new HBox();
        goals.setSpacing(20);

        VBox currentGoals = new VBox();
        currentGoals.setSpacing(10);
        currentGoals.setId("playerInfo");
        Text currentGoalsTitle = new Text("Current Goals ");
        currentGoalsTitle.setId("mediumTitle");
        Text score = new Text("Score goal: " + scoreGoal);
        score.setId("playerInfo");
        Text gold = new Text("Gold goal: " + goldGoal);
        gold.setId("playerInfo");
        Text health = new Text("Health goal: " + healthGoal);
        health.setId("playerInfo");
        Text inventory = new Text("Inventory goal: " + inventoryGoal);
        inventory.setId("playerInfo");
        currentGoals.getChildren().addAll(currentGoalsTitle, score, gold, health, inventory);

        VBox updateGoals = new VBox();
        updateGoals.setSpacing(10);
        Text updateGoalsTitle = new Text("Update Goals");
        updateGoalsTitle.setId("mediumTitle");
        scoreField.setPromptText("Enter score goal");
        scoreField.setId("textFieldGoals");
        goldField.setPromptText("Enter gold goal");
        goldField.setId("textFieldGoals");
        healthField.setPromptText("Enter health goal");
        healthField.setId("textFieldGoals");
        inventoryField.setPromptText("Enter inventory goal");
        inventoryField.setId("textFieldGoals");

        Button updateButton = new Button("Update");
        updateButton.setId("confirmGoals");

        updateButton.setOnAction(e -> {
            if (!scoreField.getText().isEmpty()) {
                score.setText("Score goal: " + scoreField.getText());
                SoundPlayer.play("src/main/resources/sounds/click.wav");
                scoreField.clear();
            }
            if (!goldField.getText().isEmpty()) {
                gold.setText("Gold goal: " + goldField.getText());
                SoundPlayer.play("src/main/resources/sounds/click.wav");
                goldField.clear();
            }
            if (!healthField.getText().isEmpty()) {
                health.setText("Health goal: " + healthField.getText());
                SoundPlayer.play("src/main/resources/sounds/click.wav");
                healthField.clear();
            }
            if (!inventoryField.getText().isEmpty()) {
                inventory.setText("Inventory goal: " + inventoryField.getText());
                SoundPlayer.play("src/main/resources/sounds/click.wav");
                inventoryField.clear();
            }
        });




        updateGoals.getChildren().addAll(updateGoalsTitle, scoreField, goldField, healthField, inventoryField, updateButton);

        goals.getChildren().addAll(currentGoals, updateGoals);
        goals.setAlignment(Pos.CENTER);

        goalsPane.getChildren().addAll(goals);


        backButtonHolder.getChildren().add(backButton);
        structure.getChildren().addAll(backButtonHolder, title, goalsPane);
        structure.setAlignment(Pos.CENTER);

        getChildren().addAll(structure);
    }
}
