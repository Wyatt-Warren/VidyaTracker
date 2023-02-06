package com.example.vidyatracker11;

public class InvalidGenreException extends Exception {
  private static final long serialVersionUID = 1L;
  
  public InvalidGenreException() {
    super("Genre is Invalid");
  }
}
