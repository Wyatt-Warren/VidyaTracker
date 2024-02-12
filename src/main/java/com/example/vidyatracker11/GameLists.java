package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Class that stores each list: played games list, unplayed games list, platform list, genre list, collections list.
public class GameLists {
    //Fields
    public static ObservableList<PlayedGame> playedList = FXCollections.observableArrayList();          //List of all PlayedGames
    public static ObservableList<UnplayedGame> unplayedList = FXCollections.observableArrayList();      //List of all UnplayedGames
    public static ObservableList<String> platformList = FXCollections.observableArrayList("PC");    //List of all platforms
    public static ObservableList<String> genreList = FXCollections.observableArrayList("Action");   //List of all genres
    public static ObservableList<GameCollection> collectionList = FXCollections.observableArrayList();  //List of all collections
    public static ObservableList<PlayedGameGoal> playedGoalList = FXCollections.observableArrayList();  //List of all played goals
    public static ObservableList<UnplayedGameGoal> unplayedGoalList = FXCollections.observableArrayList();  //List of all unplayed goals

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
}