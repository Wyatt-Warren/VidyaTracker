package com.example.vidyatracker11;

public class InvalidHoursException extends Exception {
  private static final long serialVersionUID = 1L;
  
  public InvalidHoursException() {
    super("Hours value is Invalid");
  }
}
