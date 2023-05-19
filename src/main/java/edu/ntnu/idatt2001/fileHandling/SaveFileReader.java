package edu.ntnu.idatt2001.fileHandling;

import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SaveFileReader {
  public String getImage(String filePath) throws IOException {
    File file = new File(filePath);
    BufferedReader br = new BufferedReader(new FileReader(file));

    String image = br.readLine();
    br.close();

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
      br.readLine(); // Skip image line
      name = br.readLine();
    }
    return name;
  }

  public String getImageIcon(String filePath) throws IOException {
    File file = new File(filePath);
    BufferedReader br = new BufferedReader(new FileReader(file));

    String image = br.readLine();
    br.close();

    return image;
  }



  public String getPath(String filePath) throws IOException {
    File file = new File(filePath);
    BufferedReader br = new BufferedReader(new FileReader(file));

    br.readLine(); // Skip image line
    br.readLine(); // Skip name line
    String path = br.readLine();
    br.close();

    return path;
  }

  public String getGoal(String filePath) throws IOException {
    File file = new File(filePath);
    BufferedReader br = new BufferedReader(new FileReader(file));
    br.readLine(); // Skip image line
    br.readLine(); // Skip name line
    br.readLine(); // Skip path line
    String name = br.readLine();
    br.close();

    return name;
  }

  public String getLastSeenPassage(String filePath) throws IOException {
    File file = new File(filePath);
    BufferedReader br = new BufferedReader(new FileReader(file));

    String lastSeenPassage = "";
    String line;
    while ((line = br.readLine()) != null) {
      if (line.startsWith("P:")) {
        lastSeenPassage = line.substring(2);
      }
    }
    br.close();
    return lastSeenPassage;
  }

  public String getPassageNameFromCounter(String filePath, int counter) {
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
      throw new RuntimeException(e);
    }
    return passageName;
  }

  public ArrayList<String> getInventoryFromCounter(String filePath, int counter) {
    ArrayList<String> passageInventory = new ArrayList<>();
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


  public HashMap<String, Object> getPassageParameters(String filePath, int counter) {
    HashMap<String, Object> map = new HashMap<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      boolean isMatchingCounter = false;

      while ((line = reader.readLine()) != null) {
        if (line.startsWith("C:" + counter)) {
          isMatchingCounter = true;
        } else        if (line.startsWith("C:") && isMatchingCounter) {
          break; // Reached the next counter, stop reading
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

