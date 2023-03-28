package edu.ntnu.idatt2001.fileHandling;

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
                    write();
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

    public static void write() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the filename: ");
        String fileName = scanner.nextLine().trim() + ".paths";

        List<String> passages = new ArrayList<>();

        // Ask the user to enter passages and their content
        while (true) {
            System.out.println("Enter a passage (or 'q' to quit):");
            String passage = scanner.nextLine().trim();
            if (passage.equalsIgnoreCase("q")) {
                break;
            }

            System.out.println("Enter the content of the passage:");
            String content = scanner.nextLine().trim();

            passages.add(passage);
            passages.add(content);
        }

        try {
            FileWrite writer = new FileWrite("src/main/resources/" + fileName);
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
