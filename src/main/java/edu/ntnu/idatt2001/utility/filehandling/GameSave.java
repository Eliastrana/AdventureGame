package edu.ntnu.idatt2001.utility.filehandling;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for saving the progress of the game.
 */
public class GameSave {

  public GameSave(String filePath) {
  }
  /**
   * Method for saving the progress of the game.
   *
   * @param content String to be saved
   * @param filePath path to the file
   * @throws IOException if the file is not found
   */

  public void progressSaver(String content, String filePath) throws IOException {
    try (FileWriter writer = new FileWriter(filePath, true)) {
      writer.write(content + "\n");
    }
  }
}
