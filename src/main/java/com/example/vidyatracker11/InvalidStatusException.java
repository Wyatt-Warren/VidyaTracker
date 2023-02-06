package com.example.vidyatracker11;

public class InvalidStatusException extends Exception {
  private static final long serialVersionUID = 1L;
  
  public InvalidStatusException() {
    super("Status is invalid");
  }
}
