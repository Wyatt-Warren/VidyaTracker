package com.example.vidyatracker11;

public class InvalidPlatformException extends Exception {
  private static final long serialVersionUID = 1L;
  
  public InvalidPlatformException() {
    super("Platform is Invalid");
  }
}
