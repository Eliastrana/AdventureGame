package edu.ntnu.idatt2001;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Stack;

import static javafx.scene.text.TextAlignment.CENTER;

public class GUI extends Application {


    private  TextField chosenName;
    private Text nameValue;



    public StackPane firstTrollBattle = new StackPane();{

        Stage primaryStage = new Stage();


        Text firstTroll = new Text("You have encountered a troll");
        firstTroll.setTextAlignment(CENTER);

        Button fightButton = new Button("Fight");
        fightButton.setOnAction(e -> fight(firstTrollBattle));


        Button runButton = new Button("Run");
        runButton.setOnAction(e ->
                //firstTrollBattle.getChildren().clear();
                runAway(firstTrollBattle));




        HBox fightorrun = new HBox();

        fightorrun.getChildren().addAll(fightButton, runButton);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(firstTroll, fightorrun);

        firstTrollBattle.getChildren().addAll(vbox);





    }

    public StackPane enterName = new StackPane();{


        Text enterYourName = new Text("Enter your name");
        enterYourName.setTextAlignment(CENTER);

        chosenName = new TextField();
        chosenName.setPromptText("Enter your name please:");
        chosenName.setPrefWidth(200);
        chosenName.setPrefHeight(50);
        chosenName.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
        chosenName.setAlignment(Pos.CENTER);

        Button enterButton = new Button("Enter");

        enterButton.setOnAction(e -> {
            System.out.println("Enterbutton clicked");
            System.out.println(chosenName.getText());
            nameValue.setText(chosenName.getText());

            enterName.setVisible(false);
            firstTrollBattle.setVisible(true);

        });

        VBox vbox = new VBox();
        vbox.getChildren().addAll(enterYourName, chosenName, enterButton);

        enterName.getChildren().addAll(vbox);



    }

    public StackPane welcomeScreen = new StackPane();{
        StackPane stackPane = new StackPane();
        Text welcome = new Text("Welcome to the Trollgame");
        welcome.setTextAlignment(CENTER);
        stackPane.getChildren().add(welcome);

        Image image = new Image("https://em-content.zobj.net/thumbs/320/apple/325/troll_1f9cc.png");

        ImageView imageView = new ImageView(image);

        double desiredHeight = 540;
        double desiredWidth = 720;
        imageView.setFitWidth(desiredWidth);
        imageView.setFitHeight(desiredHeight);

        Button startbutton = new Button("Start");

        startbutton.setOnAction(e -> {
            welcomeScreen.setVisible(false);
            enterName.setVisible(true);
            System.out.println("Startbutton clicked");

        });

        VBox vBox = new VBox(welcome, imageView, startbutton);

        welcomeScreen.getChildren().addAll(vBox);

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Bank");
        primaryStage.setWidth(720);
        primaryStage.setHeight(540);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        BorderPane borderPane = new BorderPane();
        borderPane.setMaxWidth(720);
        borderPane.setMaxHeight(540);

        borderPane.setStyle("-fx-background-color: #E6E8E6;");
        scrollPane.setStyle("-fx-background-color: #E6E8E6;");
        scrollPane.setContent(borderPane);

        Scene scene = new Scene(scrollPane);
        primaryStage.setScene(scene);
        primaryStage.show();

        welcomeScreen.setVisible(true);
        enterName.setVisible(false);
        firstTrollBattle.setVisible(false);

        StackPane root = new StackPane();

        root.getChildren().addAll(welcomeScreen, enterName, firstTrollBattle);
        borderPane.setTop(topInfo(primaryStage));
        borderPane.setCenter(root);


    }


    public HBox topInfo(Stage primaryStage) {



        Text currentName = new Text("Name: ");
        currentName.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        nameValue = new Text(""); // Initialize the nameValue Text object

        nameValue.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        VBox characterName = new VBox(currentName, nameValue);
        characterName.setAlignment(Pos.CENTER);


        Text currentHealth = new Text("Health: ");
        currentHealth.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Text healthValue = new Text("100");
        healthValue.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        VBox health = new VBox(currentHealth, healthValue);
        health.setAlignment(Pos.CENTER);


        Text currentGold = new Text("Gold: ");
        currentGold.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Text goldValue = new Text("100");
        goldValue.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        VBox gold = new VBox(currentGold, goldValue);
        gold.setAlignment(Pos.CENTER);

        Text currentScore = new Text("Score: ");
        currentScore.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Text scoreValue = new Text("100");
        scoreValue.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        VBox score = new VBox(currentScore, scoreValue);
        score.setAlignment(Pos.CENTER);

        Text currentInventory = new Text("Inventory: ");
        currentInventory.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Text inventoryValue = new Text("100");
        inventoryValue.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        VBox inventory = new VBox(currentInventory, inventoryValue);
        inventory.setAlignment(Pos.CENTER);

        TextArea textArea = new TextArea();
        textArea.setPrefSize(200, 200);

        Button testingSaving = new Button("Load story");
        testingSaving.setOnAction(event -> {
//            // Import the StoryFileManager class and create a new instance of it.
//            StoryFileManager storyFileManager = new StoryFileManager();
//
//            String fileContent = storyFileManager.toString();
//            textArea.setText(fileContent);
        });


//        Button testingSaving = new Button("Save");
//        testingSaving.setOnAction(event -> {
//
//            //import the StoryFileManager class and create a new instance of it. Then call the loadfile method
//            StoryFileManager storyFileManager = new StoryFileManager();
//            storyFileManager.loadFromFile("/Users/eliastrana/Documents/Systemutvikling/Programmering2/Adventuregame/src/main/resources/pathExamples/test2");
//            textArea.setText(storyFileManager.toString());
//
//        });

        HBox topInfo = new HBox(characterName, health, gold, score, inventory,textArea, testingSaving);
        topInfo.setAlignment(Pos.TOP_CENTER);
        topInfo.setSpacing(20);


        return topInfo;
    }


    public void runAway(StackPane root) {



        Text runAway = new Text("You ran away! But the troll ripped of your leg!");
        runAway.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        runAway.setTextAlignment(CENTER);

        HBox battleChoise = new HBox();

        Button runWithLeg = new Button("Pick up the leg from the ground and run");
        runWithLeg.setPrefWidth(400);
        runWithLeg.setPrefHeight(50);
        runWithLeg.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");
        runWithLeg.setTextAlignment(CENTER);

        Button runWithoutLeg = new Button("Cry and run away without your leg");
        runWithoutLeg.setPrefWidth(400);
        runWithLeg.setPrefHeight(50);
        runAway.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
        runAway.setTextAlignment(CENTER);

        battleChoise.getChildren().addAll(runWithLeg, runWithoutLeg);
        battleChoise.setAlignment(Pos.CENTER);

        HBox textPromt = new HBox();
        textPromt.setAlignment(Pos.CENTER);
        textPromt.getChildren().add(runAway);
        VBox vbox = new VBox(textPromt, battleChoise);
        vbox.setAlignment(Pos.CENTER);


        runWithLeg.setOnAction(event -> {
            System.out.println("trying to run with leg");
            root.getChildren().clear();
            runAwayWithLeg(root);
        });

        runWithoutLeg.setOnAction(event -> {
            root.getChildren().clear();
            runAwayWithoutLeg(root);
        });

        root.getChildren().add(vbox);

    }

    public void fight(StackPane root) {

        Text fight = new Text("You are too weak and the troll killed you!");
        fight.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        fight.setTextAlignment(CENTER);

        Button backToStart = new Button("Back to start");
        backToStart.setPrefWidth(400);
        backToStart.setPrefHeight(50);
        backToStart.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
        backToStart.setOnAction(event -> {
            root.getChildren().clear();
            root.getChildren().add(firstTrollBattle);

        });

        VBox textPromt = new VBox();

        textPromt.setAlignment(Pos.CENTER);

        textPromt.getChildren().add(fight);
        textPromt.getChildren().add(backToStart);

        VBox vbox = new VBox(textPromt);
        vbox.setAlignment(Pos.CENTER);

        root.getChildren().clear();
        root.getChildren().add(vbox);
    }





    public void runAwayWithoutLeg(StackPane root) {



        Text flight = new Text("You become so sad because you are without your leg, that you die ;-(");
        flight.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        flight.setTextAlignment(CENTER);

        Button backToStart = new Button("Claim your loss");
        backToStart.setPrefWidth(200);
        backToStart.setPrefHeight(50);
        backToStart.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        backToStart.setOnAction(event -> {
            try {;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        VBox vbox = new VBox(flight, backToStart);
        vbox.setAlignment(Pos.CENTER);
        root.getChildren().add(vbox);

    }

    public void runAwayWithLeg(StackPane root) {



        Text flight = new Text("You turn around and use your leg as a hammer and kill the troll ;-)");
        flight.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        flight.setTextAlignment(CENTER);

        Button backToStart = new Button("Claim your victory");
        backToStart.setPrefWidth(200);
        backToStart.setPrefHeight(50);
        backToStart.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        backToStart.setOnAction(event -> {
            try {;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        VBox vbox = new VBox(flight, backToStart);
        vbox.setAlignment(Pos.CENTER);
        root.getChildren().add(vbox);


    }


}
