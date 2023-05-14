package edu.ntnu.idatt2001.fileHandling;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;



public class SaveFileReader {


  public static String getImage(String filePath) throws IOException {
    File file = new File(filePath);
    BufferedReader br = new BufferedReader(new FileReader(file));

    String image = br.readLine();
    br.close();

    return image;
  }

  public static String getName(String filePath) throws IOException {
    File file = new File(filePath);
    BufferedReader br = new BufferedReader(new FileReader(file));

    br.readLine(); // Skip image line
    String name = br.readLine();
    br.close();

    return name;
  }


  public static String getPath(String filePath) throws IOException {
    File file = new File(filePath);
    BufferedReader br = new BufferedReader(new FileReader(file));

    br.readLine(); // Skip image line
    br.readLine(); // Skip name line
    String path = br.readLine();
    br.close();

    return path;
  }

  public static String getLastSeenPassage(String filePath) throws IOException {
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


  public static String getPassageNameFromCounter(String filePath, int counter) {
    String passageName = "";
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;

      while ((line = reader.readLine()) != null) {
        if (line.startsWith("C:" + counter)) {
          //System.out.println("Found counter");
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

  public static ArrayList<String> getInventoryFromCounter(String filePath, int counter) {
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


  public static HashMap<String, Object> getPassageParameters(String filePath, int counter) {
    HashMap<String, Object> map = new HashMap<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      boolean isMatchingCounter = false;

      while ((line = reader.readLine()) != null) {
        if (line.startsWith("C:" + counter)) {
          isMatchingCounter = true;
        } else if (line.startsWith("C:") && isMatchingCounter) {
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
      throw new RuntimeException(e);
    }

    return map;
  }


}
