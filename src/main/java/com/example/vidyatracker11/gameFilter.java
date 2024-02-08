package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class gameFilter {
    //Fields
    ObservableList<String> possibleStatuses = FXCollections.observableArrayList();
    ObservableList<String> possibleFranchises = FXCollections.observableArrayList();
    ObservableList<String> possiblePlatforms = FXCollections.observableArrayList();
    ObservableList<String> possibleGenres = FXCollections.observableArrayList();
    ObservableList<String> possibleCollections = FXCollections.observableArrayList();
    String titleContains = "";
    int minimumReleaseYear;
    int maximumReleaseYear;

    public gameFilter(ObservableList<String> possibleStatuses, ObservableList<String> possibleFranchises,
                      ObservableList<String> possiblePlatforms, ObservableList<String> possibleGenres,
                      ObservableList<String> possibleCollections,
                      String titleContains, int minimumReleaseYear, int maximumReleaseYear) {
        this.possibleStatuses = possibleStatuses;
        this.possibleFranchises = possibleFranchises;
        this.possiblePlatforms = possiblePlatforms;
        this.possibleGenres = possibleGenres;
        this.possibleCollections = possibleCollections;
        this.titleContains = titleContains;
        this.minimumReleaseYear = minimumReleaseYear;
        this.maximumReleaseYear = maximumReleaseYear;
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

    public ObservableList<String> getPossibleCollections() {
        return possibleCollections;
    }

    public String getTitleContains() {
        return titleContains;
    }

    public int getMinimumReleaseYear() {
        return minimumReleaseYear;
    }

    public int getMaximumReleaseYear() {
        return maximumReleaseYear;
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

    public void setPossibleCollections(ObservableList<String> possibleCollections) {
        this.possibleCollections = possibleCollections;
    }

    public void setTitleContains(String titleContains) {
        this.titleContains = titleContains;
    }

    public void setMinimumReleaseYear(int minimumReleaseYear) {
        this.minimumReleaseYear = minimumReleaseYear;
    }

    public void setMaximumReleaseYear(int maximumReleaseYear) {
        this.maximumReleaseYear = maximumReleaseYear;
    }
}
