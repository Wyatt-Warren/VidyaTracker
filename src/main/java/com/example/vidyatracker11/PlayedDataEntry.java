package com.example.vidyatracker11;

//Used by tableviews in the stats screen
public class PlayedDataEntry {
  private String title;
  
  private int count;
  
  private String percent;
  
  private String averageRating;

  //averageRating getter
  public String getAverageRating() {
    return averageRating;
  }

  //averageRating setter
  public void setAverageRating(String averageRating) {
    this.averageRating = averageRating;
  }

  //count getter
  public int getCount() {
    return count;
  }

  //count setter
  public void setCount(int count) {
    this.count = count;
  }

  //name getter
  public String getName() {
    return title;
  }

  //name setter
  public void setName(String name) {
    this.title = name;
  }

  //percent getter
  public String getPercent() {
    return percent;
  }

  //percent setter
  public void setPercent(String percent) {
    this.percent = percent;
  }
}
