package com.example.vidyatracker11;

public abstract class GameGoal {
    //Fields
    private String title;

    //Dates
    private int startYear;
    private int startMonth;
    private int startDay;
    private int endYear;
    private int endMonth;
    private int endDay;

    //Progress
    private int startProgress;
    private int goalProgress;

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

    public String getTitle() {
        return title;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public int getStartDay() {
        return startDay;
    }

    public int getEndYear() {
        return endYear;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public int getEndDay() {
        return endDay;
    }

    public int getStartProgress() {
        return startProgress;
    }

    public int getGoalProgress() {
        return goalProgress;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    public void setStartProgress(int startProgress) {
        this.startProgress = startProgress;
    }

    public void setGoalProgress(int goalProgress) {
        this.goalProgress = goalProgress;
    }
}
