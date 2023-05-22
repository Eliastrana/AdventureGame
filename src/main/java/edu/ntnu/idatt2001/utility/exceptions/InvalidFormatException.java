package edu.ntnu.idatt2001.utility.exceptions;

/**
 * Exception thrown when a file is not formatted correctly.
 */
public class InvalidFormatException extends Exception {
  public InvalidFormatException(String message) {
    super(message);
  }
}
