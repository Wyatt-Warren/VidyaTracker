package com.example.vidyatracker11;

public class UnplayedGame extends Game {
  
  private double hours;
  
  private String deckCompatible = "";
  
  public UnplayedGame(String title, String status, String platform,
                      String genre, int releaseYear, int releaseMonth,
                      int releaseDay) throws
          InvalidStatusException, InvalidPlatformException, InvalidGenreException,
          InvalidYearException, InvalidMonthException, InvalidDayException {
    super(title, status, platform, genre, releaseYear, releaseMonth, releaseDay);
    if (!status.equals("Backlog") && !status.equals("SubBacklog") && !status.equals("Wishlist"))
      throw new InvalidStatusException(); 
  }
  
  public void setHours(double hours) throws InvalidHoursException {
    if (hours < 0.0)
      throw new InvalidHoursException(); 
    this.hours = hours;
  }
  
  public void setHours(int hours) throws InvalidHoursException {
    if (hours < 0)
      throw new InvalidHoursException(); 
    this.hours = hours;
  }
  
  public void setStatus(String status) throws InvalidStatusException {
    if (!status.equals("Backlog") && !status.equals("SubBacklog") && !status.equals("Wishlist"))
      throw new InvalidStatusException(); 
    this.status = status;
  }
  
  public void setDeckCompatible(String deckCompatible) throws InvalidDeckStatusException {
    if (!deckCompatible.equals("Blank") && !deckCompatible.equals("") && !deckCompatible.equals("Yes") && !deckCompatible.equals("No") && !deckCompatible.equals("Maybe"))
      throw new InvalidDeckStatusException(); 
    this.deckCompatible = deckCompatible;
  }
  
  public double getHours() {
    return hours;
  }
  
  public String getDeckCompatible() {
    return deckCompatible;
  }
}
