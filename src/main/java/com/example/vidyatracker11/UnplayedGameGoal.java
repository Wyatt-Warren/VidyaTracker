package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UnplayedGameGoal extends GameGoal{
    //Fields
    //Filters
    ObservableList<String> possibleDeckStatuses = FXCollections.observableArrayList();
    int minHours;
    int maxHours;

    public UnplayedGameGoal(String title, int startYear, int startMonth, int startDay, int endYear, int endMonth,
                            int endDay, int startProgress, int goalProgress) {
        super(title, startYear, startMonth, startDay, endYear, endMonth, endDay, startProgress, goalProgress);
    }

}
