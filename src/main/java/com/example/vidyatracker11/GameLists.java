package com.example.vidyatracker11;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameLists {
  public static ObservableList<PlayedGame> playedList = FXCollections.observableArrayList();
  
  public static ObservableList<UnplayedGame> unplayedList = FXCollections.observableArrayList();
  
  public static ObservableList<String> platformList = FXCollections.observableArrayList("PC" );
  
  public static ObservableList<String> genreList = FXCollections.observableArrayList("Action");
  
  private static final Date date = new Date();
  
  private static final ZoneId timeZone = ZoneId.systemDefault();
  
  private static final LocalDate localDate = date.toInstant().atZone(timeZone).toLocalDate();
  
  public static int getCompletedThisYearCount() {
    int total = 0;
    for (PlayedGame playedGame : playedList) {
      if (playedGame.getStatus().equals("Completed") && playedGame
        .getCompletionYear() == localDate.getYear() && 
        !playedGame.getIsItShort().equals("Yes"))
        total++; 
    } 
    return total;
  }
  
  public static int getCompletedLastYearCount() {
    int total = 0;
    for (PlayedGame playedGame : playedList) {
      if (playedGame.getStatus().equals("Completed") && playedGame
        .getCompletionYear() == localDate.getYear() - 1 && 
        !playedGame.getIsItShort().equals("Yes"))
        total++; 
    } 
    return total;
  }
  
  public static int getShortCompletedThisYearCount() {
    int total = 0;
    for (PlayedGame playedGame : playedList) {
      if (playedGame.getStatus().equals("Completed") && playedGame
        .getCompletionYear() == localDate.getYear() && playedGame
        .getIsItShort().equals("Yes"))
        total++; 
    } 
    return total;
  }
  
  public static int getShortCompletedLastYearCount() {
    int total = 0;
    for (PlayedGame playedGame : playedList) {
      if (playedGame.getStatus().equals("Completed") && playedGame
        .getCompletionYear() == localDate.getYear() - 1 && playedGame
        .getIsItShort().equals("Yes"))
        total++; 
    } 
    return total;
  }
  
  public static int getTotalThisYearCount() {
    return getCompletedThisYearCount() + getShortCompletedThisYearCount();
  }
  
  public static int getTotalLastYearCount() {
    return getCompletedLastYearCount() + getShortCompletedLastYearCount();
  }
  
  public static int getPlayingCount() {
    int total = 0;
    for (PlayedGame playedGame : playedList) {
      if (playedGame.getStatus().equals("Playing"))
        total++; 
    } 
    return total;
  }
  
  public static int getCompletedCount() {
    int total = 0;
    for (PlayedGame playedGame : playedList) {
      if (playedGame.getStatus().equals("Completed") && 
        !playedGame.getIsItShort().equals("Yes"))
        total++; 
    } 
    return total;
  }
  
  public static int getShortCompletedCount() {
    int total = 0;
    for (PlayedGame playedGame : playedList) {
      if (playedGame.getStatus().equals("Completed") && playedGame
        .getIsItShort().equals("Yes"))
        total++; 
    } 
    return total;
  }
  
  public static int getHoldCount() {
    int total = 0;
    for (PlayedGame playedGame : playedList) {
      if (playedGame.getStatus().equals("On Hold"))
        total++; 
    } 
    return total;
  }
  
  public static int getBacklogCount() {
    int total = 0;
    for (UnplayedGame unplayedGame : unplayedList) {
      if (unplayedGame.getStatus().equals("Backlog"))
        total++; 
    } 
    return total;
  }
  
  public static int getSubBacklogCount() {
    int total = 0;
    for (UnplayedGame unplayedGame : unplayedList) {
      if (unplayedGame.getStatus().equals("SubBacklog"))
        total++; 
    } 
    return total;
  }
  
  public static int getWishlistCount() {
    int total = 0;
    for (UnplayedGame unplayedGame : unplayedList) {
      if (unplayedGame.getStatus().equals("Wishlist"))
        total++; 
    } 
    return total;
  }
  
  public static double getBacklogHours() {
    double total = 0.0D;
    for (UnplayedGame unplayedGame : unplayedList) {
      if (unplayedGame.getStatus().equals("Backlog"))
        total += unplayedGame.getHours(); 
    } 
    return total;
  }
  
  public static double getSubBacklogHours() {
    double total = 0.0D;
    for (UnplayedGame unplayedGame : unplayedList) {
      if (unplayedGame.getStatus().equals("SubBacklog"))
        total += unplayedGame.getHours(); 
    } 
    return total;
  }
  
  public static double getWishlistHours() {
    double total = 0.0D;
    for (UnplayedGame unplayedGame : unplayedList) {
      if (unplayedGame.getStatus().equals("Wishlist"))
        total += unplayedGame.getHours(); 
    } 
    return total;
  }
  
  public static double getTotalHours() {
    double total = 0.0D;
    for (UnplayedGame unplayedGame : unplayedList)
      total += unplayedGame.getHours(); 
    return total;
  }
}
