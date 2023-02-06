package com.example.vidyatracker11;

public class InvalidMonthException extends Exception {
  private static final long serialVersionUID = 1L;
  
  public InvalidMonthException() {
    super("Month is Invalid");
  }
}
