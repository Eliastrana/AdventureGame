package edu.ntnu.idatt2001.fileHandling;

import edu.ntnu.idatt2001.Game;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class FileDashboard {

    public static void main(String[] args) throws FileNotFoundException {

        while (true){
            System.out.println("1. Write to file");
            System.out.println("2. Read from file");
            System.out.println("3. Exit");
            System.out.println("Choose an option: ");
            Scanner scanner = new Scanner(System.in);
            FileDashboard fileDashboard = new FileDashboard();
            int choice = scanner.nextInt();
            switch (choice){
                case 1:

                    break;
                case 2:
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }
    }





    public String read(String fileName) {
        FileRead formatter = new FileRead("src/main/resources/" + fileName + ".paths");

        String formattedStory = formatter.toString();

        return formattedStory;
    }

    public String readCharacter(String filename) {
        String filePath = "src/main/resources/characters/" + filename + ".paths";

        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }
        return contentBuilder.toString();
    }



    public static void write(String text, String fileName) {
        List<String> passages = new ArrayList<>();

        // Split the text into lines and process each line as a passage and its content
        String[] lines = text.split("\\r?\\n"); // split by new line character
        for (int i = 0; i < lines.length; i += 2) {
            String passage = lines[i].trim();
            String content = (i + 1 < lines.length) ? lines[i + 1].trim() : "";
            passages.add(passage);
            passages.add(content);
        }

        try {
            FileWrite writer = new FileWrite("src/main/resources/paths/" + fileName + ".paths");
            writer.appendPathsFile(passages.toArray(new String[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void writeToFile(String input, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(input);
        }
    }

public static void gameSave(String input, String filePath) throws IOException {


        GameSave savaData = new GameSave(filePath);
        try {
            savaData.progressSaver(input, filePath);
            //System.out.println("Data written to " + filePath + " successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}







