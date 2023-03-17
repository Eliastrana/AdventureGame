package edu.ntnu.idatt2001.fileHandling;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileWrite {
    private String filePath;

    public FileWrite(String filePath) {
        this.filePath = filePath;
    }

    public void appendPathsFile(String[] passages) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));

        for (int i = 0; i < passages.length; i += 2) {
            writer.write(":: " + passages[i]);
            writer.newLine();
            String content = passages[i + 1].replace("[", "\n["); // add a new line before "["
            writer.write(content);
            writer.newLine();
            writer.newLine();
        }

        writer.close();
    }


    public String getFilePath() {
        return filePath;
    }
}
