//package edu.ntnu.idatt2001.frontend;
//
//import javafx.application.Application;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//
//public class MyGUI extends Application {
//
//    private Scene mainScene;
//    private StackPane stackPane;
//    private Button backButton;
//
//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("Navigation Example");
//
//        // Create main scene with four buttons
//        VBox buttonBox = new VBox(20);
//        buttonBox.setAlignment(Pos.CENTER);
//        Button button1 = new Button("Pane 1");
//        Button button2 = new Button("Pane 2");
//        Button button3 = new Button("Pane 3");
//        Button button4 = new Button("Pane 4");
//        buttonBox.getChildren().addAll(button1, button2, button3, button4);
//        mainScene = new Scene(buttonBox, 400, 400);
//
//        // Create stack pane to hold all panes
//        stackPane = new StackPane();
//        stackPane.setStyle("-fx-background-color: #ffffff;");
//
//        // Create pane 1 with back button
//        StackPane pane1 = new StackPane();
//        pane1.setStyle("-fx-background-color: #ffcccc;");
//        Text text1 = new Text("Pane 1");
//        text1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
//        backButton = new Button("Back to Main");
//        backButton.setOnAction(e -> stackPane.getChildren().remove(pane1));
//        pane1.getChildren().addAll(text1, backButton);
//
//        // Create pane 2 with back button
//        StackPane pane2 = new StackPane();
//        pane2.setStyle("-fx-background-color: #ccffcc;");
//        Text text2 = new Text("Pane 2");
//        text2.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
//        backButton = new Button("Back to Main");
//        backButton.setOnAction(e -> stackPane.getChildren().remove(pane2));
//        pane2.getChildren().addAll(text2, backButton);
//
//        // Create pane 3 with back button
//        StackPane pane3 = new StackPane();
//        pane3.setStyle("-fx-background-color: #ccccff;");
//        Text text3 = new Text("Pane 3");
//        text3.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
//        backButton = new Button("Back to Main");
//        backButton.setOnAction(e -> stackPane.getChildren().remove(pane3));
//        pane3.getChildren().addAll(text3, backButton);
//
//        // Create pane 4 with back button
//        StackPane pane4 = new StackPane();
//        pane4.setStyle("-fx-background-color: #ffffcc;");
//        Text text4 = new Text("Pane 4");
//        text4.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
//        backButton = new Button("Back to Main");
//        backButton.setOnAction(e -> stackPane.getChildren().remove(pane4));
//        pane4.getChildren().addAll(text4, backButton);
//
//        // Set button actions to switch scenes
//        button1.setOnAction(e -> {
//            stackPane.getChildren().add(pane1);
//            backButton.setOnAction(ev -> stackPane.getChildren().remove(pane1));
//        });
//        button2.setOnAction(e -> {
//            stackPane.getChildren().add(pane2);
//            backButton.setOnAction(ev -> stackPane.getChildren().remove(pane2));
//        });
//        button3.setOnAction(e -> {
//            stackPane.getChildren().add(pane3);
//            backButton.setOnAction(ev -> stackPane.getChildren().remove(pane3));
//        });
//        button4.setOnAction(e -> {
//            stackPane.getChildren().add(pane4);
//            backButton.setOnAction(ev -> stackPane.getChildren().remove(pane4));
//        });
//
//
//        stackPane.getChildren().add(buttonBox);
//
//        // Set main scene as root pane
//        BorderPane rootPane = new BorderPane();
//        rootPane.setCenter(stackPane);
//
//
//        // Set scene and show stage
//        primaryStage.setScene(new Scene(rootPane, 400, 400));
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}