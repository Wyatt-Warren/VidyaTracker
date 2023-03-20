package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameCollection {
    //Fields
    private String title;                                                           //Title of collection
    private final ObservableList<Game> games = FXCollections.observableArrayList(); //List of games in collection

    public GameCollection(String title){
        this.title = title;
    }

    //Title getter
    public String getTitle() {
        return title;
    }

    //Title setter
    public void setTitle(String title) {
        this.title = title;
    }

    //Games getter
    public ObservableList<Game> getGames() {
        return games;
    }

    //Return title for the list in CollectionsManageWindow
    @Override
    public String toString() {
        return title;
    }
}
