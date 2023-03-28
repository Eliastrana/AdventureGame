package edu.ntnu.idatt2001.fileHandling;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class FileDashboard {

    public static void main(String[] args) {

        while (true){
            System.out.println("1. Write to file");
            System.out.println("2. Read from file");
            System.out.println("3. Exit");
            System.out.println("Choose an option: ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    //write();
                    break;
                case 2:
                    //String formattedStory = read(fileName);
                    //System.out.println(formattedStory);
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }
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
            FileWrite writer = new FileWrite("src/main/resources/" + fileName + ".paths");
            writer.appendPathsFile(passages.toArray(new String[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public static String read(String fileName) {
        try {
            FileRead formatter = new FileRead("src/main/resources/" + fileName + ".paths");
            String formattedStory = formatter.formatPathsFile();
            return formattedStory;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
