package edu.ntnu.idatt2001.fileHandling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileDashboardTest {
    private static final String TEST_FILE_PATH = "src/main/resources/testStory.paths";

    @BeforeEach
    void setUp() {
        // Delete test file if it already exists
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testWrite() throws IOException {
        // Delete the test file if it already exists
        Path path = Paths.get(TEST_FILE_PATH);
        if (Files.exists(path)) {
            Files.delete(path);
        }

        // Write to the file
        FileDashboard.write();

        // Verify that test file was created and contains expected data
        assertTrue(Files.exists(path));
        List<String> lines = Files.readAllLines(path);
        assertEquals(8, lines.size());
        assertEquals(":: Passage 3", lines.get(0));
        assertEquals("This is the content of passage 3.", lines.get(1));
        assertEquals(":: Passage 4", lines.get(2));
        assertEquals("This is the content of passage 4. ", lines.get(3));
        assertEquals(":: Passage 5", lines.get(4));
        assertEquals("The troll has bad kondisjon.", lines.get(5));
        assertEquals(":: Passage 6", lines.get(6));
        assertEquals("The troll got drepressed.", lines.get(7));

        // Delete the test file after the test is complete
        Files.delete(path);
    }


    @Test
    void testRead() throws IOException {
        // Create test file
        List<String> lines = new ArrayList<>();
        lines.add(":: Passage 1");
        lines.add("This is the content of passage 1.");
        lines.add("");
        lines.add(":: Passage 2");
        lines.add("This is the content of passage 2.");
        Files.write(Paths.get(TEST_FILE_PATH), lines);

        String expectedOutput = ":: Passage 1\nThis is the content of passage 1.\n\n:: Passage 2\nThis is the content of passage 2.\n\n";
        assertEquals(expectedOutput, FileDashboard.read());
    }
}
