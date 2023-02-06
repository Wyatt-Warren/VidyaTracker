package com.example.vidyatracker11;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Class that stores each list: played games list, unplayed games list, platform list, genre list.
public class GameLists {
  public static ObservableList<PlayedGame> playedList = FXCollections.observableArrayList();
  
  public static ObservableList<UnplayedGame> unplayedList = FXCollections.observableArrayList();
  
  public static ObservableList<String> platformList = FXCollections.observableArrayList("PC" );
  
  public static ObservableList<String> genreList = FXCollections.observableArrayList("Action");
  
  private static final Date date = new Date();
  
  private static final ZoneId timeZone = ZoneId.systemDefault();
  
  private static final LocalDate localDate = date.toInstant().atZone(timeZone).toLocalDate();

  //returns the amount of non-short games completed in the current year.
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

  //Returns the amount of non-short games completed last year.
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

  //Returns the amount of short games completed this year.
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

  //Returns the amount of short games completed last year.
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

  //Returns the number of games completed this year, regardless of short status.
  public static int getTotalThisYearCount() {
    return getCompletedThisYearCount() + getShortCompletedThisYearCount();
  }

  //Returns the number of games completed last year, regardless of short status.
  public static int getTotalLastYearCount() {
    return getCompletedLastYearCount() + getShortCompletedLastYearCount();
  }

  //Returns the number of games with the status "Playing"
  public static int getPlayingCount() {
    int total = 0;
    for (PlayedGame playedGame : playedList) {
      if (playedGame.getStatus().equals("Playing"))
        total++; 
    } 
    return total;
  }

  //Returns the number of games with the status "Completed" and not short.
  public static int getCompletedCount() {
    int total = 0;
    for (PlayedGame playedGame : playedList) {
      if (playedGame.getStatus().equals("Completed") && 
        !playedGame.getIsItShort().equals("Yes"))
        total++; 
    } 
    return total;
  }

  //Returns the number of games with the status "Completed" that are short.
  public static int getShortCompletedCount() {
    int total = 0;
    for (PlayedGame playedGame : playedList) {
      if (playedGame.getStatus().equals("Completed") && playedGame
        .getIsItShort().equals("Yes"))
        total++; 
    } 
    return total;
  }

  //Returns the number of games with the status "On Hold"
  public static int getHoldCount() {
    int total = 0;
    for (PlayedGame playedGame : playedList) {
      if (playedGame.getStatus().equals("On Hold"))
        total++; 
    } 
    return total;
  }

  //Returns the number of games with the status "Backlog"
  public static int getBacklogCount() {
    int total = 0;
    for (UnplayedGame unplayedGame : unplayedList) {
      if (unplayedGame.getStatus().equals("Backlog"))
        total++; 
    } 
    return total;
  }

  //Returns the number of games with the status "SubBacklog"
  public static int getSubBacklogCount() {
    int total = 0;
    for (UnplayedGame unplayedGame : unplayedList) {
      if (unplayedGame.getStatus().equals("SubBacklog"))
        total++; 
    } 
    return total;
  }

  //Returns the nuber of games with the status "Wishlist"
  public static int getWishlistCount() {
    int total = 0;
    for (UnplayedGame unplayedGame : unplayedList) {
      if (unplayedGame.getStatus().equals("Wishlist"))
        total++; 
    } 
    return total;
  }

  //Returns the hours of all games in "Backlog" added together.
  public static double getBacklogHours() {
    double total = 0.0;
    for (UnplayedGame unplayedGame : unplayedList) {
      if (unplayedGame.getStatus().equals("Backlog"))
        total += unplayedGame.getHours(); 
    } 
    return total;
  }

  //Returns the hours of all games in "SubBacklog" added together.
  public static double getSubBacklogHours() {
    double total = 0.0;
    for (UnplayedGame unplayedGame : unplayedList) {
      if (unplayedGame.getStatus().equals("SubBacklog"))
        total += unplayedGame.getHours(); 
    } 
    return total;
  }

  //Returns the hours of all games in "Wishlist" added together.
  public static double getWishlistHours() {
    double total = 0.0;
    for (UnplayedGame unplayedGame : unplayedList) {
      if (unplayedGame.getStatus().equals("Wishlist"))
        total += unplayedGame.getHours(); 
    } 
    return total;
  }

  //Returns the hours of all unplayed games added together.
  public static double getTotalHours() {
    double total = 0.0;
    for (UnplayedGame unplayedGame : unplayedList)
      total += unplayedGame.getHours(); 
    return total;
  }
}
