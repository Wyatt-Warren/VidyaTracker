package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class GameFilter {
    //Fields
    ObservableList<String> possibleStatuses;
    ObservableList<String> possibleFranchises;
    ObservableList<String> possiblePlatforms;
    ObservableList<String> possibleGenres;
    ObservableList<GameCollection> possibleCollections;
    String titleContains;
    int minReleaseYear;
    int maxReleaseYear;

    public GameFilter(ObservableList<String> possibleStatuses, ObservableList<String> possibleFranchises,
                      ObservableList<String> possiblePlatforms, ObservableList<String> possibleGenres,
                      ObservableList<GameCollection> possibleCollections,
                      String titleContains, int minReleaseYear, int maxReleaseYear) {
        this.possibleStatuses = possibleStatuses;
        this.possibleFranchises = possibleFranchises;
        this.possiblePlatforms = possiblePlatforms;
        this.possibleGenres = possibleGenres;
        this.possibleCollections = possibleCollections;
        this.titleContains = titleContains;
        this.minReleaseYear = minReleaseYear;
        this.maxReleaseYear = maxReleaseYear;
    }

    //Returns true if given game is in any selected collection
    public boolean gameInAnyCollection(Game game){
        for(GameCollection collection : possibleCollections)
            //Check each collection for given game
            if(collection.getGames().contains(game))
                //Collection contains game
                return true;

        //Was not found
        return false;
    }

    public ObservableList<String> getPossibleStatuses() {
        return possibleStatuses;
    }

    public ObservableList<String> getPossibleFranchises() {
        return possibleFranchises;
    }

    public ObservableList<String> getPossiblePlatforms() {
        return possiblePlatforms;
    }

    public ObservableList<String> getPossibleGenres() {
        return possibleGenres;
    }

    public ObservableList<GameCollection> getPossibleCollections() {
        return possibleCollections;
    }

    public String getTitleContains() {
        return titleContains;
    }

    public int getMinReleaseYear() {
        return minReleaseYear;
    }

    public int getMaxReleaseYear() {
        return maxReleaseYear;
    }

    public void setPossibleStatuses(ObservableList<String> possibleStatuses) {
        this.possibleStatuses = possibleStatuses;
    }

    public void setPossibleFranchises(ObservableList<String> possibleFranchises) {
        this.possibleFranchises = possibleFranchises;
    }

    public void setPossiblePlatforms(ObservableList<String> possiblePlatforms) {
        this.possiblePlatforms = possiblePlatforms;
    }

    public void setPossibleGenres(ObservableList<String> possibleGenres) {
        this.possibleGenres = possibleGenres;
    }

    public void setPossibleCollections(ObservableList<GameCollection> possibleCollections) {
        this.possibleCollections = possibleCollections;
    }

    public void setTitleContains(String titleContains) {
        this.titleContains = titleContains;
    }

    public void setMinReleaseYear(int minReleaseYear) {
        this.minReleaseYear = minReleaseYear;
    }

    public void setMaxReleaseYear(int maxReleaseYear) {
        this.maxReleaseYear = maxReleaseYear;
    }
}
