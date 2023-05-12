package edu.ntnu.idatt2001.fileHandling;

import edu.ntnu.idatt2001.GUI.PaneGenerator;
import edu.ntnu.idatt2001.Passage;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;


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
    public static String backOnePassage(String filePath) {
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("P:")) {
                    lines.add(line.substring(2));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (lines.size() < 1) {
            throw new IllegalArgumentException("The file does not have a penultimate line");
        }
        return lines.get(lines.size() - 1);
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

        br.readLine(); // Skip image line
        br.readLine(); // Skip name line
        br.readLine(); // Skip path line

        String lastSeenPassage = "";
        String line;
        while ((line = br.readLine()) != null) {
            lastSeenPassage = line;
        }

        br.close();

        return lastSeenPassage;
    }


    public static String findWordBeforePng(String filename, String path) {
        String filePath = path + "/" + filename;
        String line = null;
        String word = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while ((line = reader.readLine()) != null) {
                if (line.contains(filename)) {
                    int index = line.indexOf(filename);
                    if (index > 0) {
                        int startIndex = index - 1;
                        while (startIndex >= 0 && Character.isLetter(line.charAt(startIndex))) {
                            startIndex--;
                        }
                        word = line.substring(startIndex + 1, index);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return word;
    }









}
