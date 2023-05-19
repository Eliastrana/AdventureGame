package edu.ntnu.idatt2001.fileHandling;

import java.io.FileWriter;
import java.io.IOException;

public class GameSave {

  public GameSave(String filePath) {
  }

  public void progressSaver(String content, String filePath) throws IOException {
    try (FileWriter writer = new FileWriter(filePath, true)) {
      writer.write(content + "\n");
    }
  }
}
