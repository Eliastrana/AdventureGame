package edu.ntnu.idatt2001.utility.filehandling;

import edu.ntnu.idatt2001.utility.exceptions.InvalidFormatException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * A class that reads the game file.
 */
public class SaveFileReader {

  /**
   * Reads the file and returns the character image path.
   *
   * @param filePath String path to the file
   * @return String path to the character image
   * @throws InvalidFormatException if the file is not found
   */
  public String getImage(String filePath) throws InvalidFormatException {
    File file = new File(filePath);
    String image;
    try(BufferedReader br = new BufferedReader(new FileReader(file))) {

      image = br.readLine();
    }
    catch (IOException e) {
      throw new InvalidFormatException(e.getMessage());
    }

    return image;

  }

  /**
   * Reads the file and return if the first passage exists.
   *
   * @param filePath String path to the file
   * @return boolean if the first passage exists
   * @throws IOException if the file is not found
   */
  public boolean getFirstPassageExisting(String filePath) throws IOException {
    File file = new File(filePath);

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        if (line.startsWith("P:")) {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * Reads the file and returns the name of the game.
   *
   * @param filePath String path to the file
   * @return String name of the game
   * @throws IOException if the file is not found
   */

  public String getName(String filePath) throws IOException {
    File file = new File(filePath);
    String name;
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      br.readLine();
      name = br.readLine();
    }
    return name;
  }


  /**
   * Reads the file and returns the path to the character.
   *
   * @param filePath String path to the file
   * @return String path to the character
   * @throws IOException if the file is not found
   */
  public String getPath(String filePath) throws IOException {
    File file = new File(filePath);
    String path;
    try(BufferedReader br = new BufferedReader(new FileReader(file))) {
      br.readLine();
      br.readLine();
      path = br.readLine();

    }
    return path;
  }

  /**
   * Reads the file and return the path to the goal file.
   *
   * @param filePath String path to the file
   * @return String path to the goal file
   * @throws IOException if the file is not found
   */

  public String getGoal(String filePath) throws IOException {
    File file = new File(filePath);
    String goal;
    try(BufferedReader br = new BufferedReader(new FileReader(file))) {
      br.readLine();
      br.readLine();
      br.readLine();
      goal = br.readLine();

    }

    return goal;
  }

  /**
   * Reads the file and returns the last passage played.
   *
   * @param filePath String path to the file
   * @return String last passage played
   * @throws IOException if the file is not found
   */
  public String getLastSeenPassage(String filePath) throws IOException {
    String lastSeenPassage = "";
    String line;
    File file = new File(filePath);
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {

      while ((line = br.readLine()) != null) {
        if (line.startsWith("P:")) {
          lastSeenPassage = line.substring(2);
        }
      }
    }
    return lastSeenPassage;
  }

  /**
   * Reads the file and returns the passage name with the given counter.
   *
   * @param filePath String path to the file
   * @param counter int counter of the passage
   * @return String passage name
   * @throws InvalidFormatException if file is on invalid format
   */

  public String getPassageNameFromCounter(String filePath, int counter) throws InvalidFormatException {
    String passageName = "";
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;

      while ((line = reader.readLine()) != null) {
        if (line.startsWith("C:" + counter)) {
          while ((line = reader.readLine()) != null) {
            if (line.startsWith("P:")) {
              passageName = line.substring(2);
              break;
            }
          }
        }
      }
    } catch (IOException e) {
      throw new InvalidFormatException(e.getMessage());
    }
    return passageName;
  }

  /**
   * Reads the file and returns the inventory based on the counter.
   *
   * @param filePath String path to the file
   * @param counter int counter of the passage
   * @return List of String inventory
   * @throws IOException if the file is not found
   */
  public List<String> getInventoryFromCounter(String filePath, int counter) throws IOException {
    List<String> passageInventory = new ArrayList<>();
    boolean isMatchingCounter = false;

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.startsWith("C:" + counter)) {
          isMatchingCounter = true;
        } else if (line.startsWith("C:") && isMatchingCounter) {
          break; // Reached the next counter, stop reading
        }

        if (isMatchingCounter && line.startsWith("I:")) {
          String[] items = line.substring(2).replaceAll("\\[|\\]", "").split(", ");
          passageInventory.addAll(Arrays.asList(items));
        }
      }
    } catch (IOException e) {
      throw new IOException(e.getMessage());
    }
    return passageInventory;
  }

  /**
   * Reads the file and returns the counter of the passage with the given title.
   *
   * @param filePath String path to the file
   * @param passageTitle String title of the passage
   * @return int counter of the passage
   * @throws IOException if the file is not found
   * @throws IllegalArgumentException if the counter is not a valid number
   */
  public int getCounterFromPassageTitle(String filePath, String passageTitle) throws IOException {
    int counter = -1;
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.startsWith("C:")) {
          // Found a counter, update the current counter
          String counterAsString = line.substring(2).trim(); // Skip "C:"
          counter = Integer.parseInt(counterAsString);
        } else if (line.startsWith("P:" + passageTitle)) {
          // Found the passage, return the last counter
          return counter;
        }
      }
    } catch (IOException e) {
      throw new IOException(e.getMessage());
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Counter is not a valid number", e);
    }
    throw new IllegalArgumentException("Passage title not found");
  }


  /**
   * Reads the file and returns the passage parameters based on the counter.
   *
   * @param filePath String path to the file
   * @param counter int counter of the passage
   * @return Map of String and Object passage parameters
   * @throws IOException if the file is not found
   */
  public Map<String, Object> getPassageParameters(String filePath, int counter) throws IOException {
    HashMap<String, Object> map = new HashMap<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      boolean isMatchingCounter = false;

      while ((line = reader.readLine()) != null) {
        if (line.startsWith("C:" + counter)) {
          isMatchingCounter = true;
        } else        if (line.startsWith("C:") && isMatchingCounter) {
          break;
        }

        if (isMatchingCounter) {
          if (line.startsWith("H:")) {
            map.put("health", Integer.parseInt(line.substring(2))); // Parse the string to an int
          } else if (line.startsWith("G:")) {
            map.put("gold", Integer.parseInt(line.substring(2))); // Parse the string to an int
          } else if (line.startsWith("S:")) {
            map.put("score", Integer.parseInt(line.substring(2))); // Parse the string to an int
          }
        }
      }
    } catch (IOException e) {
      throw new IOException(e.getMessage());
    }
    return map;
  }
}

