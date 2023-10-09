package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.nio.charset.StandardCharsets;

//Class that stores each list: played games list, unplayed games list, platform list, genre list, collections list.
public class GameLists {
    //Fields
    public static ObservableList<PlayedGame> playedList = FXCollections.observableArrayList();          //List of all PlayedGames
    public static ObservableList<UnplayedGame> unplayedList = FXCollections.observableArrayList();      //List of all UnplayedGames
    public static ObservableList<String> platformList = FXCollections.observableArrayList("PC");    //List of all platforms
    public static ObservableList<String> genreList = FXCollections.observableArrayList("Action");   //List of all genres
    public static ObservableList<GameCollection> collectionList = FXCollections.observableArrayList();  //List of all collections

    //Returns the amount of non-short games completed in the given year.
    public static int getCompletedYearCount(int year) {
        //Local variables
        int total = 0;  //Count of applicable games

        for (PlayedGame playedGame: playedList) {
            //Iterate for each PlayedGame
            if (playedGame.getStatus().equals("Completed") &&
                    playedGame.getCompletionYear() == year &&
                    !playedGame.getShortStatus().equals("Yes"))
                //If game is completed, not short, and completed in the given year, increment total
                total++;
        }

        return total;
    }

    //Returns the amount of short games completed in the given year.
    public static int getShortCompletedYearCount(int year) {
        //Local variables
        int total = 0;  //Count of applicable games

        for (PlayedGame playedGame: playedList) {
            //Iterate for each PlayedGame
            if (playedGame.getStatus().equals("Completed") &&
                    playedGame.getCompletionYear() == year &&
                    playedGame.getShortStatus().equals("Yes"))
                //If game is completed, short, and completed in the given year, increment total
                total++;
        }

        return total;
    }

    //Returns the number of games completed this year, regardless of short status.
    public static int getTotalYearCount(int year) {
        return getCompletedYearCount(year) + getShortCompletedYearCount(year);
    }

    //Returns the number of PlayedGames with the given status and not short.
    public static int getPlayedStatusCount(String status) {
        //Local variables
        int total = 0;  //Count of applicable games

        for (PlayedGame playedGame: playedList) {
            //Iterate for each PlayedGame
            if (playedGame.getStatus().equals(status) &&
                    !playedGame.getShortStatus().equals("Yes"))
                //If game has the given status and is not short, increment total
                total++;
        }

        return total;
    }

    //Returns the number of PlayedGames with the given status that are short.
    public static int getPlayedStatusShortCount(String status) {
        //Local variables
        int total = 0;  //Count of applicable games

        for (PlayedGame playedGame: playedList) {
            //Iterate for each PlayedGame
            if (playedGame.getStatus().equals(status) &&
                    playedGame.getShortStatus().equals("Yes"))
                //If game has the given status and is short, increment total
                total++;
        }

        return total;
    }

    //Returns the number of UnplayedGames with the given status
    public static int getUnplayedStatusCount(String status) {
        //Local variables
        int total = 0;  //Count of applicable games

        for (UnplayedGame unplayedGame: unplayedList) {
            //Iterate for each UnplayedGame
            if (unplayedGame.getStatus().equals(status))
                //If game has the given status, increment total
                total++;
        }

        return total;
    }

    //Returns the total hours of all games with the given status
    public static double getStatusHours(String status) {
        //Local variables
        double total = 0.0; //Count of total hours

        for (UnplayedGame unplayedGame: unplayedList) {
            //Iterate for each UnplayedGame
            if (unplayedGame.getStatus().equals(status))
                //If game has the given status, increment total
                total += unplayedGame.getHours();
        }

        return total;
    }

    //Returns true if a collection with the given title exists
    public static boolean collectionTitleTaken(String title){
        for (GameCollection collection: collectionList)
            //Check each collection for given title
            if (collection.getTitle().equals(title))
                //Collection has given title
                return true;

        //Was not found
        return false;
    }

    //Returns true if a collection with the given title exists, excludes a collection from search
    public static boolean collectionTitleTaken(String title, GameCollection excluded){
        for (GameCollection collection: collectionList)
            //Check each collection for given title
            if (collection != excluded && collection.getTitle().equals(title))
                //Collection has given title and is not the excluded collection
                return true;

        //Was not found
        return false;
    }

    //Returns the hours of all unplayed games added together.
    public static double getTotalHours() {
        return getStatusHours("Backlog") + getStatusHours("SubBacklog") + getStatusHours("Wishlist");
    }
}