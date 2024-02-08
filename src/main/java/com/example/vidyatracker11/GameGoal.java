package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class GameGoal {
    //Fields
    String title;

    //Dates
    int startYear;
    int startMonth;
    int startDay;
    int endYear;
    int endMonth;
    int endDay;

    //Progress
    int startProgress;
    int goalProgress;

    //Filters
    ObservableList<String> possibleStatuses = FXCollections.observableArrayList();
    ObservableList<String> possibleFranchises = FXCollections.observableArrayList();
    ObservableList<String> possiblePlatforms = FXCollections.observableArrayList();
    ObservableList<String> possibleGenres = FXCollections.observableArrayList();
    ObservableList<String> possibleCollections = FXCollections.observableArrayList();
    String titleContains = "";
    int minimumYear;
    int maximumYear;

    public GameGoal(String title, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay,
                    int startProgress, int goalProgress){
        this.title = title;
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.startDay = startDay;
        this.endYear = endYear;
        this.endMonth = endMonth;
        this.endDay = endDay;
        this.startProgress = startProgress;
        this.goalProgress = goalProgress;
    }

}
