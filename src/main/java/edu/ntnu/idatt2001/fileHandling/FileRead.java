package edu.ntnu.idatt2001.fileHandling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileRead {
    private String filePath;

    public FileRead(String filePath) {
        this.filePath = filePath;
    }

    public String formatPathsFile() throws IOException {
        StringBuilder formattedStory = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        boolean inBlock = false;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("::")) {
                if (inBlock) {
                    formattedStory.append("\n");
                }
                formattedStory.append(line).append("\n");
                formattedStory.append(reader.readLine()).append("\n");
                inBlock = true;
            } else if (line.startsWith("[")) {
                formattedStory.append(line).append("\n");
            } else if (line.trim().isEmpty()) {
                formattedStory.append("\n");
                inBlock = false;
            }
        }

        reader.close();
        return formattedStory.toString();
    }

    public String getFilePath() throws UnsupportedOperationException {
        if (!filePath.equals("src/main/resources/testStory.paths")) {
            throw new UnsupportedOperationException("Invalid file path provided");
        }
        return filePath;
    }






}
