//package edu.ntnu.idatt2001;
//
//import javafx.application.Application;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
////PRØV Å BRUK DETTE TIL NOE I DEN KODEN SOM FUNGERER
////DETTE FUNGERER IKKE
//
//public class AdventureGame extends Application {
//
//    private Map<String, String[]> paths = new HashMap<>();
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        loadPaths("story.paths");
//
//        Scene scene = new Scene(createRoot(), 400, 300);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    private void loadPaths(String path) throws IOException {
//        BufferedReader reader = new BufferedReader(new FileReader(path));
//        String line;
//        String currentKey = "";
//        while ((line = reader.readLine()) != null) {
//            if (line.startsWith("::")) {
//                currentKey = line.substring(3).trim();
//                paths.put(currentKey, new String[2]);
//            } else if (line.contains("(") && line.contains(")")) {
//                int bracketIndex = line.indexOf("(");
//                String text = line.substring(0, bracketIndex).trim();
//                String target = line.substring(bracketIndex + 1, line.indexOf(")")).trim();
//                paths.get(currentKey)[0] = text;
//                paths.get(currentKey)[1] = target;
//            }
//        }
//        reader.close();
//    }
//
//    private VBox createRoot() {
//        VBox root = new VBox();
//        root.setPadding(new Insets(10));
//        root.setSpacing(10);
//
//        String currentKey = "Welcome";
//        final String[] targetKey = new String[1]; // Declare final array to store target key
//
//        while (true) {
//            String[] currentPath = paths.get(currentKey);
//            Text text = new Text(currentPath[0]);
//            Button[] buttons = new Button[paths.size()];
//            int buttonIndex = 0;
//            for (String key : paths.keySet()) {
//                if (key.equals(currentKey)) {
//                    continue;
//                }
//                buttons[buttonIndex] = new Button(key);
//                buttons[buttonIndex].setOnAction(event -> {
//                    targetKey[0] = paths.get(event.getSource().toString()).toString();
//                    currentKey = targetKey[0].substring(1, targetKey[0].length() - 1);
//                    root.getChildren().clear();
//                    root.getChildren().addAll(text, createButtons(buttons));
//                });
//                buttonIndex++;
//            }
//            root.getChildren().addAll(text, createButtons(buttons));
//            if (currentPath[1].equals("back to start")) {
//                currentKey = "Welcome";
//            } else {
//                currentKey = currentPath[1];
//            }
//        }
//        return root;
//    }
//
//
//    private VBox createButtons(Button[] buttons) {
//        VBox buttonBox = new VBox();
//        buttonBox.setSpacing(5);
//        for (Button button : buttons) {
//            if (button != null) {
//                buttonBox.getChildren().add(button);
//            }
//        }
//        return buttonBox;
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
