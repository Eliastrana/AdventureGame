package edu.ntnu.idatt2001.fileHandling;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
public class PlayerRegister {

    public static void saveTextToFile(String text, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            String[] words = text.split("\\s+"); // Split the text into words
            for (int i = 0; i < words.length; i++) {
                writer.write(words[i]);
                if (i < words.length - 1) {
                    writer.write(","); // Add a comma between words
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving the file: " + e.getMessage());
        }
    }


    public String characterInforVariable(String filePath) {
        String output = null;
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();
            String[] values = line.split(",");

            String name = values[0];
            int health = Integer.parseInt(values[1]);
            int gold = Integer.parseInt(values[2]);
            int score = Integer.parseInt(values[3]);

            output = "Name: " + name + " Health: " + health + " Gold: " + gold + " Score: " + score;
            System.out.println(output);

            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }


    public static void main(String[] args) {

    }
}
