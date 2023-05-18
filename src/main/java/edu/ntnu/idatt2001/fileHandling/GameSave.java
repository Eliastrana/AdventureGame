package edu.ntnu.idatt2001.fileHandling;

import java.io.FileWriter;
import java.io.IOException;

public class GameSave {
  private String filePath = "src/main/resources/savaData/save.txt";

  public GameSave(String filePath) {
    this.filePath = filePath;
  }

  public void progressSaver(String content, String filePath) throws IOException {

    FileWriter writer = new FileWriter(filePath, true);
    writer.write(content + "\n");
    writer.close();
  }


}
