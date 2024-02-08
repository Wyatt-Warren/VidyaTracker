package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlayedGameGoal extends GameGoal {
    //Fields
    //Filters
    ObservableList<String> possibleShortStatuses = FXCollections.observableArrayList();
    ObservableList<Integer> possibleRatings = FXCollections.observableArrayList();
    ObservableList<String> possible100PercentStatuses = FXCollections.observableArrayList();

    public PlayedGameGoal(String title, int startYear, int startMonth, int startDay, int endYear, int endMonth,
                          int endDay, int startProgress, int goalProgress) {
        super(title, startYear, startMonth, startDay, endYear, endMonth, endDay, startProgress, goalProgress);
    }
}
