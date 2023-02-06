package com.example.vidyatracker11;

public class InvalidDeckStatusException extends Exception {
  private static final long serialVersionUID = 1L;
  
  public InvalidDeckStatusException() {
    super("Toaster Compatibility Status is Invalid");
  }
}
