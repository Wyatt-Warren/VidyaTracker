package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class unplayedGameFilter extends gameFilter{
    //Fields
    ObservableList<String> possibleDeckStatuses = FXCollections.observableArrayList();
    int minHours;
    int maxHours;

    public unplayedGameFilter(ObservableList<String> possibleStatuses, ObservableList<String> possibleFranchises,
                              ObservableList<String> possiblePlatforms, ObservableList<String> possibleGenres,
                              ObservableList<String> possibleCollections, ObservableList<String> possibleDeckStatuses,
                              String titleContains, int minimumReleaseYear, int maximumReleaseYear, int minHours,
                              int maxHours) {
        super(possibleStatuses, possibleFranchises, possiblePlatforms, possibleGenres, possibleCollections, titleContains, minimumReleaseYear, maximumReleaseYear);
        this.possibleDeckStatuses = possibleDeckStatuses;
        this.minHours = minHours;
        this.maxHours = maxHours;
    }

    public ObservableList<String> getPossibleDeckStatuses() {
        return possibleDeckStatuses;
    }

    public int getMinHours() {
        return minHours;
    }

    public int getMaxHours() {
        return maxHours;
    }

    public void setPossibleDeckStatuses(ObservableList<String> possibleDeckStatuses) {
        this.possibleDeckStatuses = possibleDeckStatuses;
    }

    public void setMinHours(int minHours) {
        this.minHours = minHours;
    }

    public void setMaxHours(int maxHours) {
        this.maxHours = maxHours;
    }
}
