package edu.ntnu.idatt2001.frontend;


import edu.ntnu.idatt2001.fileHandling.PlayerRegister;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Pane3 extends StackPane {

    public TextField createPlayerName = new TextField();

    TextField setPlayerHealth = new TextField();
    TextField setPlayerGold = new TextField();
    TextField setPlayerScore = new TextField();


    ComboBox<String> playerInventory = new ComboBox<>();

    public Pane3() {


        VBox structure = new VBox();

        structure.getStylesheets().add("/Style.css");

        createPlayerName.setId("textField");
        setPlayerHealth.setId("textField");
        setPlayerGold.setId("textField");
        setPlayerScore.setId("textField");







        VBox playerCreation = new VBox();

        Text createPlayer = new Text("Create player:");
        createPlayer.setId("title");
        createPlayerName.setPromptText("Enter player name");
        setPlayerHealth.setPromptText("Enter player health");
        setPlayerGold.setPromptText("Enter player gold");
        setPlayerScore.setPromptText("Enter player score");

        playerInventory.setPromptText("Enter player inventory");
        populatePlayerInventory();

        Button createPlayerButton = new Button("Create player");
        createPlayerButton.setId("confirmButton");
        playerCreation.getChildren().addAll(createPlayer, createPlayerName, setPlayerHealth, setPlayerGold, setPlayerScore, playerInventory, createPlayerButton);
        playerCreation.setSpacing(10);


        createPlayerButton.setOnAction(e -> {

            String playerStats = createPlayerName.getText() +" " + setPlayerHealth.getText() + " " + setPlayerGold.getText() + " " + setPlayerScore.getText() + " " + playerInventory.getValue();

            PlayerRegister.saveTextToFile(playerStats, "src/main/resources/characters/"+createPlayerName.getText()+".paths");

            System.out.println(playerStats+".paths");

            createPlayerName.clear();
            setPlayerHealth.clear();
            setPlayerGold.clear();
            setPlayerScore.clear();


        });
        setStyle("-fx-background-color: #e3ffcc;");


        Button backButton = new Button("Back to Main");
        backButton.setId("backButton");
        backButton.setAlignment(Pos.TOP_LEFT);
        backButton.setOnAction(e -> SceneSwitcher.switchToMainMenu());

        playerCreation.setAlignment(Pos.CENTER);

        structure.getChildren().addAll(backButton, playerCreation);
        structure.setSpacing(20);

        getChildren().addAll(structure);

    }
    private void populatePlayerInventory() {
        playerInventory.getItems().addAll("Sword", "Rock", "Stick", "Flashlight");
    }

}

