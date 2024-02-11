package com.example.vidyatracker11;

public class UnplayedGameGoal extends GameGoal{
    //Fields
    private int endProgress;
    private UnplayedGameFilter filter;

    public UnplayedGameGoal(String title, int startYear, int startMonth, int startDay, int endYear, int endMonth,
                            int endDay, int startProgress, int goalProgress, int endProgress, UnplayedGameFilter filter) {
        super(title, startYear, startMonth, startDay, endYear, endMonth, endDay, startProgress, goalProgress);
        this.endProgress = endProgress;
        this.filter = filter;
    }

    public int getEndProgress() {
        return endProgress;
    }

    public void setEndProgress(int endProgress) {
        this.endProgress = endProgress;
    }

    public UnplayedGameFilter getFilter() {
        return filter;
    }

    public void setFilter(UnplayedGameFilter filter) {
        this.filter = filter;
    }
}
