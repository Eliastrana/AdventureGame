package edu.ntnu.idatt2001.utility.exceptions;

/**
 * Exception thrown when an action is not formatted correctly.
 */
public class InvalidActionFormatException extends Exception {
  public InvalidActionFormatException(String message) {
    super(message);
  }
}
