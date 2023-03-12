package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameCollection {
    private String title;
    private ObservableList<Game> games = FXCollections.observableArrayList();

    public GameCollection(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ObservableList<Game> getGames() {
        return games;
    }

    @Override
    public String toString() {
        return title;
    }
}
