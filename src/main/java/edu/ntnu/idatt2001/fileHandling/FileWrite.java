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
            writer.write(passages[i]);
            String passage = passages[i + 1];
            writer.newLine();
            if (passage.contains("[")) {
                // Extract the links from the passage
                String[] links = passage.split("\\[");
                String lastLinkRef = "";
                for (int j = 1; j < links.length; j++) {
                    String link = links[j];
                    int endIndex = link.indexOf("]");
                    String linkText = link.substring(0, endIndex);
                    String linkRef = link.substring(endIndex + 2, link.length() - 1);
                    if (!linkRef.equals(lastLinkRef) && !linkText.trim().isEmpty() && !linkRef.trim().isEmpty()) {
                        writer.write("[" + linkText + "](" + linkRef + ")");
                        writer.newLine();
                        lastLinkRef = linkRef;
                    }

                }
            } else {
                // Write the passage without links
                writer.write(passage);
            }
            writer.newLine();
        }

        writer.close();
    }








    public String getFilePath() {
        return filePath;
    }
}
