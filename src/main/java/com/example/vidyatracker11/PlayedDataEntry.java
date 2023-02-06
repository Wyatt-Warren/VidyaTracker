package com.example.vidyatracker11;

public class PlayedDataEntry {
  private String title;
  
  private int count;
  
  private String percent;
  
  private String averageRating;
  
  public String getAverageRating() {
    return averageRating;
  }
  
  public void setAverageRating(String averageRating) {
    this.averageRating = averageRating;
  }
  
  public int getCount() {
    return count;
  }
  
  public void setCount(int count) {
    this.count = count;
  }
  
  public String getName() {
    return title;
  }
  
  public void setName(String name) {
    this.title = name;
  }
  
  public String getPercent() {
    return percent;
  }
  
  public void setPercent(String percent) {
    this.percent = percent;
  }
}
