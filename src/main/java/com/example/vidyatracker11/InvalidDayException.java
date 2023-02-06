package com.example.vidyatracker11;

public class InvalidDayException extends Exception {
  private static final long serialVersionUID = 1L;
  
  public InvalidDayException() {
    super("Day is Invalid");
  }
}
