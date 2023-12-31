package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.utility.filehandling.FileWrite;
import edu.ntnu.idatt2001.utility.filehandling.GameSave;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that writes progress to file.
 */
public class FileDashboard {
  static final String ERROR_MSG = "Error writing to file: ";

  /**
   * Private constructor to prevent instantiation.
   */
  private FileDashboard() {
  }

  static String endingPath = ".paths";

  /**
   * Writes the given text to the given file.
   *
   * @param text     String text to write to file
   * @param fileName String name of the file to write to
   */
  public static void write(String text, String fileName) throws IOException {
    try {
      List<String> passages = new ArrayList<>();

      // Split the text into lines and process each line as a passage and its content
      String[] lines = text.split("\\r?\\n"); // split by new line character
      for (int i = 0; i < lines.length; i += 2) {
        String passage = lines[i].trim();
        String content = (i + 1 < lines.length) ? lines[i + 1].trim() : "";
        passages.add(passage);
        passages.add(content);
      }

      FileWrite writer = new FileWrite("src/main/resources/paths/" + fileName + endingPath);
      writer.appendPathsFile(passages.toArray(new String[0]));
    } catch (IOException e) {
      throw new IOException(ERROR_MSG + e.getMessage());
    }
  }

  /**
   * A method that takes the input from the user and writes it to a file.
   *
   * @param input    String input from the user
   * @param filePath String path to the file
   * @throws IOException if the file is not found
   */
  public static void gameSave(String input, String filePath) throws IOException {


    GameSave savaData = new GameSave(filePath);
    try {
      savaData.progressSaver(input, filePath);
    } catch (IOException e) {
      throw new IOException(ERROR_MSG + e.getMessage());
    }
  }

  /**
   * A method that writes the goals to a file.
   *
   * @param input        String input from the user
   * @param saveLocation String path to the file
   * @throws IOException if the file is not found
   */
  public static void writeGoals(String input, String saveLocation) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveLocation, true))) {
      writer.write(input);
      writer.newLine(); // Add a new line after the appended content
    } catch (IOException e) {
      throw new IOException(ERROR_MSG + e.getMessage());
    }
  }

}