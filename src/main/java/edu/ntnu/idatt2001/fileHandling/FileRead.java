package edu.ntnu.idatt2001.fileHandling;

import edu.ntnu.idatt2001.Action.Action;
import edu.ntnu.idatt2001.Link;
import edu.ntnu.idatt2001.Passage;
import edu.ntnu.idatt2001.Story;

import javax.imageio.stream.ImageInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileRead {
    private String filePath;

    Passage passage = null;


    public FileRead(String filePath) {
        this.filePath = filePath;
    }



    public List<Passage> formatPathsFile() throws IOException {
        List<Passage> passages = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        String tittel = null;
        String innhold = null;
        List<Link> links = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("::")) {
                // Start of new passage
                if (tittel != null) {
                    Passage passage = new Passage(tittel, innhold);
                    System.out.println(passage.getTitle());
                    passages.add(passage);
                    tittel = null;
                    innhold = null;
                }
                tittel = line;
                //System.out.println("Tittel: " + tittel);
            } else if (!line.startsWith("[") && !line.trim().isEmpty()) {
                // Passage content

                innhold = (innhold == null) ? line : innhold + "\n" + line;
                //System.out.println("Innhold: " + innhold);

            } else if (line.startsWith("[")) {
                // Passage link

                int startIndex = line.indexOf("[") + 1;
                int endIndex = line.indexOf("]");
                String textInsideBrackets = line.substring(startIndex, endIndex);


                int startIndex2 = line.indexOf("(") + 1;
                int endIndex2 = line.indexOf(")");
                String textInsideParentheses = line.substring(startIndex2, endIndex2);


                links.add(new Link(textInsideBrackets,textInsideParentheses, new ArrayList<>()));



            }


            else if (line.trim().isEmpty()) {
                // End of block
                if (tittel != null) {
                    Passage passage = new Passage(tittel, innhold);
                    passage.setLinks(links);
                    passages.add(passage);

                    tittel = null;
                    innhold = null;
                    links = new ArrayList<>();
                }

            }
        }

        // Add last passage
        if (tittel != null) {
            Passage passage = new Passage(tittel, innhold);
            passage.setLinks(links);
            passages.add(passage);
        }
        for (Passage passage : passages) {
            System.out.println(passage.getTitle());
            System.out.println(passage.getContent());
            System.out.println(passage.getLinks());


        }

        return passages;
    }


    public String getFilePath() throws UnsupportedOperationException {
        if (!filePath.equals("src/main/resources/testStory.paths")) {
            throw new UnsupportedOperationException("Invalid file path provided");
        }
        return filePath;
    }


    @Override
    public String toString() {

        FileRead fileRead = new FileRead(filePath);

        String passageText = "";
        try {
            passageText = String.valueOf(fileRead.formatPathsFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return passageText;
    }


    public static void main(String[] args) {
        try {
            String filePath = "src/main/resources/pickFile.paths";
            FileRead fileRead = new FileRead(filePath);

            String passageText = String.valueOf(fileRead.formatPathsFile());
            System.out.println(passageText);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }





}
