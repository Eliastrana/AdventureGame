package edu.ntnu.idatt2001.utility.filehandling;

import edu.ntnu.idatt2001.utility.exceptions.InvalidFormatException;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SaveFileReader {
  public String getImage(String filePath) throws IOException {
    File file = new File(filePath);
    String image;
    try(BufferedReader br = new BufferedReader(new FileReader(file))) {

      image = br.readLine();

    }

    return image;
  }

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


  public String getName(String filePath) throws IOException {
    File file = new File(filePath);
    String name;
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      br.readLine();
      name = br.readLine();
    }
    return name;
  }


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

  public List<String> getInventoryFromCounter(String filePath, int counter) {
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
      throw new RuntimeException(e);
    }
    return passageInventory;
  }

  public int getCounterFromPassageTitle(String filePath, String passageTitle) {
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
      throw new RuntimeException(e);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Counter is not a valid number", e);
    }
    throw new IllegalArgumentException("Passage title not found");
  }


  public Map<String, Object> getPassageParameters(String filePath, int counter) {
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
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("Error reading file");
      alert.setContentText("There was an error reading the file. Please try again.");
      alert.showAndWait();

    }

    return map;
  }
}

