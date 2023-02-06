package com.example.vidyatracker11;

public class InvalidShortStatusException extends Exception {
  private static final long serialVersionUID = 1L;
  
  public InvalidShortStatusException() {
    super("Short Status is Invalid");
  }
}
