package com.example.vidyatracker11;

public class InvalidRatingException extends Exception {
  private static final long serialVersionUID = 1L;
  
  public InvalidRatingException() {
    super("Rating is invalid");
  }
}
