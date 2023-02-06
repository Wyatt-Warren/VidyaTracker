package com.example.vidyatracker11;

public class InvalidYearException extends Exception {
  private static final long serialVersionUID = 1L;
  
  public InvalidYearException() {
    super("Year is Invalid");
  }
}
