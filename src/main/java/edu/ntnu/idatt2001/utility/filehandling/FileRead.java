package edu.ntnu.idatt2001.utility.filehandling;

import edu.ntnu.idatt2001.model.Link;
import edu.ntnu.idatt2001.model.Passage;
import edu.ntnu.idatt2001.model.action.Action;
import edu.ntnu.idatt2001.model.action.ActionsFactory;
import edu.ntnu.idatt2001.utility.exceptions.InvalidActionFormatException;
import edu.ntnu.idatt2001.utility.exceptions.MissingLinkException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Class for reading the file.
 */
public class FileRead {

  private final String filePath;
  private static final String ErrorAtLine = "Error at line ";

  /**
   * Constructor for FileRead.
   *
   * @param filePath filePath
   */
  public FileRead(String filePath) {
    this.filePath = filePath;
  }

  /**
   * Method for reading the file.
   *
   * @return list of passages
   */
  public List<Passage> formatPathsFile() {
    List<Passage> passages = new ArrayList<>();
    int lineNumber = 0;

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      String title = null;
      StringBuilder contentBuilder = new StringBuilder();
      List<Link> links = new ArrayList<>();

      while ((line = reader.readLine()) != null) {
        lineNumber++;

        try {
          if (line.startsWith("::")) {
            // Start of new passage
            if (title != null) {
              Passage passage = new Passage(title, contentBuilder.toString());
              passage.setLinks(links);
              passages.add(passage);
              contentBuilder.setLength(0); // Reset content builder
              links = new ArrayList<>();
            }
            title = line.substring(2).trim(); // Remove the "::" prefix and trim the title
          } else if (line.startsWith("{")) {
            ActionsFactory actionsFactory = new ActionsFactory();
            Action action = actionsFactory.createAction(line);
            if (action != null) {

              // Add action to the last link
              if (!links.isEmpty()) {
                links.get(links.size() - 1).addAction(action);
              } else {
                throw new MissingLinkException("Action found before any link.");
              }
            } else {
              throw new InvalidActionFormatException("Invalid action format.");
            }
          } else if (!line.startsWith("[") && !line.trim().isEmpty()) {
            // Passage content
            if (contentBuilder.length() > 0) {
              contentBuilder.append("\n");
            }
            contentBuilder.append(line);
          } else if (line.startsWith("[")) {
            // Passage link
            int startIndex = line.indexOf("[") + 1;
            int endIndex = line.indexOf("]");
            String textInsideBrackets = line.substring(startIndex, endIndex);

            int startIndex2 = line.indexOf("(") + 1;
            int endIndex2 = line.indexOf(")");
            String textInsideParentheses = line.substring(startIndex2, endIndex2);

            links.add(new Link(textInsideBrackets, textInsideParentheses, new ArrayList<>()));
          }
        } catch (StringIndexOutOfBoundsException e) {
          alertError(ErrorAtLine + lineNumber + ":\n" + "String index out of bounds");
          e.printStackTrace();
        } catch (InvalidActionFormatException | MissingLinkException e) {
          alertError(ErrorAtLine + lineNumber + ":\n" + e.getMessage());
          e.printStackTrace();
        }
      }
      // Add the last passage
      if (title != null) {
        Passage passage = new Passage(title, contentBuilder.toString());
        passage.setLinks(links);
        passages.add(passage);
      }

    } catch (FileNotFoundException e) {
      alertError("File not found: " + filePath + "\n" + e.getMessage());
    } catch (IOException e) {
      alertError("Error reading file:" + "\n" + filePath + "\n" + e.getMessage());
    } catch (Exception e) {
      alertError("An unexpected error occurred while reading the file: " + e.getMessage());
      e.printStackTrace();
    }

    return passages;
  }


  /**
   * Method for alerting the user of an error.
   *
   * @param message message
   */
  private void alertError(String message) {
    Platform.runLater(() -> {
      Alert alert = new Alert(AlertType.ERROR);
      alert.getDialogPane().setId("alertBox");

      alert.setTitle("Invalid Format Structure");
      alert.setHeaderText("Something went wrong opening the file");
      alert.setContentText(message);
      alert.showAndWait();
    });
  }


  @Override
  public String toString() {
    return String.valueOf(this.formatPathsFile());
  }
}
