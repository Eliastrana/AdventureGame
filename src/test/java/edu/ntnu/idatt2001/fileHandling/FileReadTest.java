package edu.ntnu.idatt2001.fileHandling;

import edu.ntnu.idatt2001.Passage;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileReadTest {

    @Test
    public void testFormatPathsFile() throws IOException {
        // Create a new FileRead object and call the formatPathsFile() method
        FileRead fileRead = new FileRead("src/main/resources/test.paths");
        List<Passage> passages = fileRead.formatPathsFile();

        // Assert that the returned list is not null and contains two elements
        assertNotNull(passages);
        assertEquals(2, passages.size());

        // Assert that the first passage has the correct title, content, and links
        Passage firstPassage = passages.get(0);
        assertEquals(":: Start", firstPassage.getTitle());
        assertEquals("This is the start of the story.\n\n[Click here to go to the next passage.](:: Next)", firstPassage.getContent());
        assertEquals(1, firstPassage.getLinks().size());
        assertEquals("Click here to go to the next passage.", firstPassage.getLinks().get(0).getText());
        //assertEquals(":: Next", firstPassage.getLinks().get(0).getTargetId());

        // Assert that the second passage has the correct title, content, and links
        Passage secondPassage = passages.get(1);
        assertEquals(":: Next", secondPassage.getTitle());
        assertEquals("This is the next passage.\n\n[Click here to go back to the start.](:: Start)", secondPassage.getContent());
        assertEquals(1, secondPassage.getLinks().size());
        assertEquals("Click here to go back to the start.", secondPassage.getLinks().get(0).getText());
        //assertEquals(":: Start", secondPassage.getLinks().get(0).getTargetId());
    }
}
