package edu.ntnu.idatt2001.utility.exceptions;

/**
 * Exception thrown when a link is missing.
 */
public class MissingLinkException extends Exception {
  public MissingLinkException(String message) {
    super(message);
  }
}
