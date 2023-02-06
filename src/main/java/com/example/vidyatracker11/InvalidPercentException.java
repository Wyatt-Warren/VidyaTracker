package com.example.vidyatracker11;

public class InvalidPercentException extends Exception {
  private static final long serialVersionUID = 1L;
  
  public InvalidPercentException() {
    super("100% Status is Invalid");
  }
}
