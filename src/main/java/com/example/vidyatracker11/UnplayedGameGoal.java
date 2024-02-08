package com.example.vidyatracker11;

import javafx.collections.FXCollections;

public class UnplayedGameGoal extends GameGoal{
    //Fields
    private UnplayedGameFilter filter;

    public UnplayedGameGoal(String title, int startYear, int startMonth, int startDay, int endYear, int endMonth,
                            int endDay, int startProgress, int goalProgress, UnplayedGameFilter filter) {
        super(title, startYear, startMonth, startDay, endYear, endMonth, endDay, startProgress, goalProgress);
        this.filter = filter;
    }

    public UnplayedGameFilter getFilter() {
        return filter;
    }

    public void setFilter(UnplayedGameFilter filter) {
        this.filter = filter;
    }
}
