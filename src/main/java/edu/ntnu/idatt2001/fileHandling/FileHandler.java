package edu.ntnu.idatt2001.fileHandling;

import edu.ntnu.idatt2001.Link;

import edu.ntnu.idatt2001.Passage;
import edu.ntnu.idatt2001.Story;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private File historyFile;

    private static final String FILE_EXTENSION = ".paths";

    public static void saveToFile(String title, List<Passage> passages, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + FILE_EXTENSION))) {
            writer.write(title);
            writer.newLine();
            writer.newLine();
            for (Passage passage : passages) {
                writer.write("::" + passage.getTitle());
                writer.newLine();
                writer.write(passage.getContent());
                writer.newLine();
                for (Link link : passage.getLinks()) {
                    writer.write(link.getText() + " (" + link.getReference() + ")");
                    writer.newLine();
                }
                writer.newLine();
            }
        }
    }

    public static Story readFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String title = reader.readLine();
            reader.readLine(); // skip empty line
            List<Passage> passages = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("::")) {
                    String passageTitle = line.substring(2);
                    String content = reader.readLine();
                    List<Link> links = new ArrayList<>();
                    while ((line = reader.readLine()) != null && !line.isEmpty()) {
                        String[] parts = line.split("\\s*\\(\\s*");
                        String text = parts[0];
                        String reference = parts[1].substring(0, parts[1].length() - 1);
                        links.add(new Link(text, reference));
                    }
                    passages.add(new Passage(passageTitle, content, links));
                }
            }
            return new Story(title, (Passage) passages);
        }
    }

    public List<String> readFile(File file) {
        List<String> history = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                history.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        historyFile = file;
        return history;
    }



    public void writeFile(File file, List<String> content) throws IOException {
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            for (String line : content) {
                writer.write(line);
                writer.write(System.lineSeparator());
            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }


    public List<String> getHistory() {
        if (historyFile != null) {
            return readFile(historyFile);
        } else {
            return new ArrayList<String>();
        }
    }

    private final String HISTORY_FILE = "history.txt";
    private final int MAX_HISTORY_SIZE = 10;
    private List<String> history;

    public FileHandler() {
        // Initialize the history list
        history = new ArrayList<>();

        // Load the history from the file
        File file = new File(HISTORY_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    history.add(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }







    private void addHistory(String path) throws IOException {
        // Add the path to the history list
        history.add(path);

        // If the history list is too long, remove the oldest entry
        if (history.size() > MAX_HISTORY_SIZE) {
            history.remove(0);
        }

        // Write the history to the history file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE))) {
            for (String line : history) {
                writer.write(line);
                writer.newLine();
            }
        }
    }


}
