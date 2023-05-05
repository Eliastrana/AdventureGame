package edu.ntnu.idatt2001.fileHandling;

import edu.ntnu.idatt2001.GUI.PaneGenerator;
import javafx.stage.Stage;

import java.io.*;


public class SaveFileReader {




    public static String fileParser(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String image = br.readLine();
        String name = br.readLine();
        String path = br.readLine();
        String lastSeenPassage = "";

        String line;
        while ((line = br.readLine()) != null) {
            lastSeenPassage = line;
        }

        br.close();

        return image +"\n"+ name + "\n" + path + "\n" + lastSeenPassage;
    }



    public static void openSavedGame() throws IOException {

        String openFile = fileParser("src/main/resources/saveData/save.txt");

        String[] split = openFile.split("\n");
        String image = split[0];
        String name = split[1];
        String path = split[2];
        String lastSeenPassage = split[3];




    }






    public static void main(String[] args) {
        try {
            System.out.println(fileParser("src/main/resources/saveData/Eliastirsdag.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
