package edu.ntnu.idatt2001.fileHandling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;


public class FileDashboard {
  static String endingPath = ".paths";

  public String read(String fileName) {
    FileRead formatter = new FileRead("src/main/resources/paths" + fileName + endingPath);

    return formatter.toString();
  }

  public String readCharacter(String filename) {
    String filePath = "src/main/resources/characters/" + filename + endingPath;

    StringBuilder contentBuilder = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        contentBuilder.append(line).append("\n");
      }
    } catch (IOException e) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("File not found");
      alert.setContentText("The file " + filePath + " was not found.");
      alert.showAndWait();
      return null;
    }
    return contentBuilder.toString();
  }


  public static void write(String text, String fileName) {
    List<String> passages = new ArrayList<>();

    // Split the text into lines and process each line as a passage and its content
    String[] lines = text.split("\\r?\\n"); // split by new line character
    for (int i = 0; i < lines.length; i += 2) {
      String passage = lines[i].trim();
      String content = (i + 1 < lines.length) ? lines[i + 1].trim() : "";
      passages.add(passage);
      passages.add(content);
    }

    try {
      FileWrite writer = new FileWrite("src/main/resources/paths/" + fileName + endingPath);
      writer.appendPathsFile(passages.toArray(new String[0]));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public static void gameSave(String input, String filePath) throws IOException {


    GameSave savaData = new GameSave(filePath);
    try {
      savaData.progressSaver(input, filePath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  //A method that takes the input from the user and writes it to a file
  public static void writeGoals(String input, String saveLocation) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveLocation, true))) {
      writer.write(input);
      writer.newLine(); // Add a new line after the appended content
    }
  }


}







