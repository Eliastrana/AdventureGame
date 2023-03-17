//package edu.ntnu.idatt2001.fileHandling;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//public class FileReadTest {
//
//
//    // Test that the file is read correctly
//
//
//    @Test
//    public void testFilePath() throws UnsupportedOperationException {
//        // Create a FileRead object and check that the file path is correct
//        String filePath = "src/main/resources/testStory.paths";
//        FileRead fileRead = new FileRead(filePath);
//        assertEquals(filePath, fileRead.getFilePath());
//    }
//
////    @Test
////    public void testInvalidFilePath() {
////        // Create a FileRead object with an invalid file path and check that an exception is thrown
////        String filePath = "/invalid/path//file.txt";
////        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> new FileRead(filePath));
////        assertEquals("File path not set", exception.getMessage());
////    }
//
//}
