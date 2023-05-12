package edu.ntnu.idatt2001.fileHandling;

import edu.ntnu.idatt2001.*;
import edu.ntnu.idatt2001.Action.Action;
import edu.ntnu.idatt2001.Action.ActionsFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileRead {

    private final String filePath;

    public FileRead(String filePath) {
        this.filePath = filePath;
    }

    public List<Passage> formatPathsFile() throws IOException {
        List<Passage> passages = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String title = null;
            StringBuilder contentBuilder = new StringBuilder();
            List<Link> links = new ArrayList<>();
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

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
                    Action action = ActionsFactory.createAction(line);
                    if (action != null) {
                        // Add action to the last link
                        if (!links.isEmpty()) {
                            links.get(links.size() - 1).addAction(action);
                        } else {
                            System.err.println("Error at line " + lineNumber + ": Action found before any link.");
                        }
                    } else {
                        System.err.println("Error at line " + lineNumber + ": Invalid action format.");
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
            }
            // Add the last passage
            if (title != null) {
                Passage passage = new Passage(title, contentBuilder.toString());
                passage.setLinks(links);
                passages.add(passage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return passages;
    }


    @Override
    public String toString() {
        String passageText = "";
        try {
            passageText = String.valueOf(this.formatPathsFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return passageText;
    }

    public static void main(String[] args) {
        try {
            String filePath = "src/main/resources/paths/hauntedHouseWithActions.paths";
            FileRead fileRead = new FileRead(filePath);

            for (Passage passage : fileRead.formatPathsFile()) {
                System.out.println(passage);
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
