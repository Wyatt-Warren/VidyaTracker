package com.example.vidyatracker11;

public class UnplayedDataEntry {
  private String title;
  
  private int count;
  
  private String percent;
  
  private String totalHours;
  
  public String getTotalHours() {
    return totalHours;
  }
  
  public void setTotalHours(String totalHours) {
    this.totalHours = totalHours;
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
