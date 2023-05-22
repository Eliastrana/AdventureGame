package edu.ntnu.idatt2001.utility.filehandling;

import edu.ntnu.idatt2001.model.Player;
import edu.ntnu.idatt2001.model.PlayerBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.control.Alert;


/**
 * Class for saving the progress of the game.
 */
public class PlayerRegister {

  /**
   * Method for saving the progress of the game.
   *
   * @param text String to be saved
   * @param filename path to the file
   */
  public void saveTextToFile(String text, String filename) {
    if (text.isBlank()) {
      throw new IllegalArgumentException("Text is blank");
    }
    if (filename.isBlank()) {
      throw new IllegalArgumentException("Filename is blank");
    }
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
      String[] words = text.split("\\s+"); // Split the text into words
      for (int i = 0; i < words.length; i++) {
        writer.write(words[i]);
        if (i < words.length - 1) {
          writer.write(","); // Add a comma between words
        }
      }
    } catch (IOException e) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("File not found");
      alert.setContentText("The file " + filename + " was not found.");
      alert.showAndWait();
    }
  }

  /**
   * Method for loading the progress of the game.
   *
   * @param filePath path to the file
   * @return Player object
   */
  public Player characterInfoVariable(String filePath) {
    try (FileReader fileReader = new FileReader(filePath);
         BufferedReader bufferedReader = new BufferedReader(fileReader)) {

      String line = bufferedReader.readLine();
      String[] values = line.split(",");

      String name = values[0];
      int health = Integer.parseInt(values[1]);
      int gold = Integer.parseInt(values[2]);
      int score = Integer.parseInt(values[3]);
      String inventory = values[4];


      return new PlayerBuilder<Player>()
              .setName(name)
              .setHealth(health)
              .setGold(gold)
              .setScore(score)
              .addToInventory(inventory)
              .build();
    } catch (Exception e) {
      throw new IllegalArgumentException("File not found");
    }
  }
}
