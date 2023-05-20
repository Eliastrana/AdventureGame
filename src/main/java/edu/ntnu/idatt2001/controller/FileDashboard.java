package edu.ntnu.idatt2001.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.ntnu.idatt2001.utility.filehandling.FileWrite;
import edu.ntnu.idatt2001.utility.filehandling.GameSave;


public class FileDashboard {
  static String endingPath = ".paths";

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