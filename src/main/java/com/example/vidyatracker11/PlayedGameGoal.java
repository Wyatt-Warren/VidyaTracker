package com.example.vidyatracker11;

public class PlayedGameGoal extends GameGoal {
    //Fields
    private PlayedGameFilter filter;

    public PlayedGameGoal(String title, int startYear, int startMonth, int startDay, int endYear, int endMonth,
                          int endDay, int startProgress, int goalProgress, PlayedGameFilter filter) {
        super(title, startYear, startMonth, startDay, endYear, endMonth, endDay, startProgress, goalProgress);
        this.filter = filter;
    }

    public PlayedGameFilter getFilter() {
        return filter;
    }

    public void setFilter(PlayedGameFilter filter) {
        this.filter = filter;
    }
}
