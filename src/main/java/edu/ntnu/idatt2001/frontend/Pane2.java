package edu.ntnu.idatt2001.frontend;


import edu.ntnu.idatt2001.fileHandling.FileDashboard;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Pane2 extends StackPane {

    public Pane2() {
        setStyle("-fx-background-color: #ccd5ff;");

        VBox structure = new VBox();
        structure.getStylesheets().add("Style.css");
        structure.setSpacing(20);
        VBox vBox = new VBox();

        VBox gameCreation = new VBox();
        gameCreation.setAlignment(Pos.CENTER);
        gameCreation.setSpacing(20);
        gameCreation.getStylesheets().add("Style.css");


        TextField writeGameSaveName = new TextField();
        writeGameSaveName.setPromptText("Write game save name");
        writeGameSaveName.setId("textField");

        TextField writeGameFieldTitle = new TextField();
        writeGameFieldTitle.setPromptText("Write game field title");
        writeGameFieldTitle.setId("textField");

        TextArea writeGameContent = new TextArea();
        writeGameContent.setPromptText("Write game content");
        writeGameContent.setId("textArea");

        HBox gameButton1 = new HBox();
        TextField writeGameButtonText1 = new TextField();
        writeGameButtonText1.setPromptText("Write game button text 1");
        writeGameButtonText1.setId("textField");
        TextField writeGameButtonTarget1 = new TextField();
        writeGameButtonTarget1.setPromptText("Write game button target 1");
        writeGameButtonTarget1.setId("textField");
        gameButton1.getChildren().addAll(writeGameButtonText1, writeGameButtonTarget1);
        gameButton1.setAlignment(Pos.CENTER);
        gameButton1.setSpacing(20);

        HBox gameButton2 = new HBox();
        TextField writeGameButtonText2 = new TextField();
        writeGameButtonText2.setPromptText("Write game button text 2");
        writeGameButtonText2.setId("textField");

        TextField writeGameButtonTarget2 = new TextField();
        writeGameButtonTarget2.setPromptText("Write game button target 2");
        writeGameButtonTarget2.setId("textField");
        gameButton2.setAlignment(Pos.CENTER);
        gameButton2.setSpacing(20);

        gameButton2.getChildren().addAll(writeGameButtonText2, writeGameButtonTarget2);


        Button writeGameButton = new Button("Write game");
        writeGameButton.setAlignment(Pos.CENTER);
        writeGameButton.setId("navigationButton");
        writeGameButton.setOnAction(e -> {
            String fileName = writeGameSaveName.getText(); // add .paths extension to filename

            String titleAndText = ":: " + writeGameFieldTitle.getText() +"\n"+ writeGameContent.getText() +"\n";
            String buttonAndLink = "";

            String buttonAndLink1 = "["+ writeGameButtonText1.getText() + "]"+"("+writeGameButtonTarget1.getText() +")"+"\n";
            String buttonAndLink2 = "["+ writeGameButtonText2.getText() +"]"+"("+ writeGameButtonTarget2.getText()+")";


            if (writeGameButtonText1.getText().isBlank() || writeGameButtonTarget1.getText().isBlank()){
                buttonAndLink1 = "";
            }
            if (writeGameButtonText2.getText().isBlank() || writeGameButtonTarget2.getText().isBlank()){
                buttonAndLink2 = "";
            }

            buttonAndLink = buttonAndLink1 + buttonAndLink2;
            String gameContent = titleAndText + buttonAndLink;
            System.out.println(gameContent);

            FileDashboard.write(gameContent, fileName ); // pass the game content and filename to the write method

            writeGameFieldTitle.setText("");
            writeGameContent.setText("");
            writeGameButtonText1.setText("");
            writeGameButtonTarget1.setText("");
            writeGameButtonText2.setText("");
            writeGameButtonTarget2.setText("");

            System.out.println("Game saved to " + fileName);
        });

        gameCreation.getChildren().addAll(writeGameSaveName,writeGameFieldTitle,writeGameContent,gameButton1, gameButton2, writeGameButton);

        vBox.getChildren().addAll(gameCreation);






        Button backButton = new Button("Back to Main");
        backButton.setAlignment(Pos.TOP_LEFT);
        backButton.setOnAction(e -> SceneSwitcher.switchToMainMenu());

        structure.getChildren().addAll(backButton, vBox);

        getChildren().addAll(structure);
    }
}

