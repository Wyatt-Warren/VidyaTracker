package com.example.vidyatracker11;

public abstract class Game {
  
  protected String status;
  
  private String title;
  
  private String platform;
  
  private String genre;
  
  private int releaseYear;
  
  private int releaseMonth;
  
  private int releaseDay;
  
  private String franchise;
  
  public Game(String title, String status, String platform,
              String genre, int releaseYear, int releaseMonth,
              int releaseDay) throws
          InvalidPlatformException, InvalidGenreException, InvalidYearException,
          InvalidMonthException, InvalidDayException {
    this.status = status;
    this.title = title;
    this.platform = platform;
    this.genre = genre;
    this.releaseYear = releaseYear;
    this.releaseMonth = releaseMonth;
    this.releaseDay = releaseDay;
    this.franchise = title;
    boolean inList = false;
    int i;
    for (i = 0; i < GameLists.platformList.size(); i++) {
      if (platform.equals(GameLists.platformList.get(i))) {
        inList = true;
        break;
      } 
    } 
    if (!inList)
      throw new InvalidPlatformException(); 
    inList = false;
    for (i = 0; i < GameLists.genreList.size(); i++) {
      if (genre.equals(GameLists.genreList.get(i))) {
        inList = true;
        break;
      } 
    } 
    if (!inList)
      throw new InvalidGenreException(); 
    if (releaseYear < 0)
      throw new InvalidYearException(); 
    if (releaseMonth < 0 || releaseMonth > 12)
      throw new InvalidMonthException(); 
    if (releaseDay < 0 || releaseDay > 31)
      throw new InvalidDayException(); 
    this.platform = platform;
  }
  
  public abstract void setStatus(String paramString) throws InvalidStatusException;
  
  public void setFranchise(String franchise) {
    this.franchise = franchise;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public void setPlatform(String platform) throws InvalidPlatformException {
    boolean inList = false;
    for (int i = 0; i < GameLists.platformList.size(); i++) {
      if (platform.equals(GameLists.platformList.get(i))) {
        inList = true;
        break;
      } 
    } 
    if (!inList)
      throw new InvalidPlatformException(); 
    this.platform = platform;
  }
  
  public void setGenre(String genre) throws InvalidGenreException {
    boolean inList = false;
    for (int i = 0; i < GameLists.genreList.size(); i++) {
      if (genre.equals(GameLists.genreList.get(i))) {
        inList = true;
        break;
      } 
    } 
    if (!inList)
      throw new InvalidGenreException(); 
    this.genre = genre;
  }
  
  public void setReleaseYear(int releaseYear) throws InvalidYearException {
    if (releaseYear < 0)
      throw new InvalidYearException(); 
    this.releaseYear = releaseYear;
  }
  
  public void setReleaseMonth(int releaseMonth) throws InvalidMonthException {
    if (releaseMonth < 0 || releaseMonth > 12)
      throw new InvalidMonthException(); 
    this.releaseMonth = releaseMonth;
  }
  
  public void setReleaseDay(int releaseDay) throws InvalidDayException {
    if (releaseDay < 0 || releaseDay > 31)
      throw new InvalidDayException(); 
    this.releaseDay = releaseDay;
  }
  
  public String getStatus() {
    return status;
  }
  
  public String getTitle() {
    return title;
  }
  
  public String getPlatform() {
    return platform;
  }
  
  public String getGenre() {
    return genre;
  }
  
  public int getReleaseYear() {
    return releaseYear;
  }
  
  public int getReleaseMonth() {
    return releaseMonth;
  }
  
  public int getReleaseDay() {
    return releaseDay;
  }
  
  public String getFranchise() {
    return franchise;
  }
}
