package edu.ntnu.idatt2001.fileHandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.ntnu.idatt2001.Story;
import edu.ntnu.idatt2001.Passage;
import edu.ntnu.idatt2001.Link;



public class StoryFileManager {




    // The file extension for the story files
    private static final String FILE_EXTENSION = ".paths";

    private static final String FILE_NAME = "/Users/eliastrana/Documents/Systemutvikling/Programmering2/Adventuregame/src/main/resources/pathExamples/test2";


    /**
     * Saves a story to a file with the given filename.
     * @param filename the name of the file to save the story to
     * @param story the story to save to the file
     */
    public static void saveToFile(String filename, Story story) {
        try (FileWriter writer = new FileWriter(filename + FILE_EXTENSION)) {
            // Write the title
            writer.write(story.getTitle() + "\n\n");

            // Write each passage
            for (Passage passage : story.getPassages()) {
                writer.write("::" + passage.getTitle() + "\n");
                writer.write(passage.getContent() + "\n");
                for (Link link : passage.getLinks()) {
                    writer.write("[" + link.getText() + "](" + link.getReference() + ")\n");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving the story to file.");
            e.printStackTrace();
        }
    }


    public static Story loadFromFile(String filename) {
        Story story = null;
        try (Scanner scanner = new Scanner(new File(filename + FILE_EXTENSION))) {
            // Read the title
            String title = scanner.nextLine();
            scanner.nextLine(); // skip empty line

            // Read the passages
            List<Passage> passages = new ArrayList<>();
            Passage openingPassage = null;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("::")) {
                    // Start of new passage
                    String passageTitle = line.substring(2);
                    String content = scanner.nextLine();
                    List<Link> links = new ArrayList<>();
                    while (scanner.hasNextLine()) {
                        line = scanner.nextLine();
                        if (line.isEmpty()) {
                            // End of passage
                            break;
                        }
                        int linkStart = line.indexOf("[");
                        int linkEnd = line.indexOf("]");
                        String linkText = line.substring(linkStart + 1, linkEnd);
                        String linkReference = line.substring(linkEnd + 2, line.length() - 1);
                        links.add(new Link(linkText, linkReference));
                    }
                    Passage passage = new Passage(passageTitle, content, links);
                    passages.add(passage);
                    if (openingPassage == null) {
                        // First passage is opening passage
                        openingPassage = passage;
                    }
                }
            }
            story = new Story(title, openingPassage, passages);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename + FILE_EXTENSION);
        } catch (Exception e) {
            System.out.println("An error occurred while loading the story from file.");
            e.printStackTrace();
        }
        return story;
    }



    public String toString() {
        StringBuilder sb = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(FILE_NAME + FILE_EXTENSION))) {
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + FILE_NAME + FILE_EXTENSION);
        } catch (Exception e) {
            System.out.println("An error occurred while loading the story from file.");
            e.printStackTrace();
        }
        return sb.toString();
    }





}
