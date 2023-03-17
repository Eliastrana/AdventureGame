//package edu.ntnu.idatt2001.fileHandling;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//public class FileWriteTest {
//
//    @Test
//    public void testAppendPathsFile() throws IOException {
//        // Set up the test by creating a temporary file
//        Path tempFile = Files.createTempFile("testfile", ".txt");
//        String filePath = tempFile.toAbsolutePath().toString();
//
//        // Create a FileWrite object and write some data to the file
//        String[] passages = {"Passage 1", "This is the first passage.", "Passage 2", "This is the second passage."};
//        FileWrite fileWrite = new FileWrite(filePath);
//        fileWrite.appendPathsFile(passages);
//
//        // Read the data from the file and check that it matches what we wrote
//        BufferedReader reader = new BufferedReader(new FileReader(filePath));
//        assertEquals(":: Passage 1", reader.readLine());
//        assertEquals("This is the first passage.", reader.readLine());
//        assertEquals("", reader.readLine());
//        assertEquals(":: Passage 2", reader.readLine());
//        assertEquals("This is the second passage.", reader.readLine());
//        assertEquals("", reader.readLine());
//
//        // Clean up the temporary file
//        Files.delete(tempFile);
//    }
//
//    @Test
//    public void testFilePath() {
//        // Create a FileWrite object and check that the file path is correct
//        String filePath = "/path/to/file.txt";
//        FileWrite fileWrite = new FileWrite(filePath);
//        assertEquals(filePath, fileWrite.getFilePath());
//    }
//
////    @Test
////    public void testInvalidFilePath() {
////        // Create a FileWrite object with an invalid file path and check that an exception is thrown
////        String filePath = "/invalid/path/to/file.txt";
////        assertThrows(IOException.class, () -> new FileWrite(filePath));
////    }
////}
