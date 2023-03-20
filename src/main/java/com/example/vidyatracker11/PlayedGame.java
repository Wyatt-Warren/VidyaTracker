package com.example.vidyatracker11;

//A played game stored in the played game list.
public class PlayedGame extends Game {
    //Fields
    private int rating;                 //Rating of game
    private String shortStatus = "";    //Short status of game
    private int completionYear = 0;     //Completion year of game
    private int completionMonth = 0;    //Completion month of game
    private int completionDay = 0;      //Completion day of  game
    private String percent100 = "";     //100% status of game

    public PlayedGame(String title, String status, String platform,
                      String genre, int releaseYear, int releaseMonth,
                      int releaseDay) {
        super(title, status, platform, genre, releaseYear, releaseMonth, releaseDay);

        if (!status.equals("Playing") && !status.equals("Completed") && !status.equals("On Hold"))
            //Throw an error if status is not playing, completed, or on hold
            throw new IllegalArgumentException();
    }

    //Status setter
    public void setStatus(String status) {
        if (!status.equals("Playing") && !status.equals("Completed") && !status.equals("On Hold"))
            //Throw an error if status is not playing, completed, or on hold
            throw new IllegalArgumentException();

        this.status = status;
    }

    //Rating setter
    public void setRating(int rating) {
        if (0 > rating || rating > 10)
            //Throw an error if rating is not between 0 and 10
            throw new IllegalArgumentException();

        this.rating = rating;
    }

    //Short status setter
    public void setShortStatus(String shortStatus) {
        if (!shortStatus.equals("") && !shortStatus.equals("Yes") && !shortStatus.equals("No"))
            //Throw an error if Short status is not blank, yes, or no
            throw new IllegalArgumentException();

        this.shortStatus = shortStatus;
    }

    //Completion Year setter
    public void setCompletionYear(int completionYear) {
        if (completionYear < 0)
            //Throw an error if completion year is negative
            throw new IllegalArgumentException();

        this.completionYear = completionYear;
    }

    //Completion month setter
    public void setCompletionMonth(int completionMonth) {
        if (completionMonth < 0 || completionMonth > 12)
            //Throw an error if completion month is not between 0 and 12
            throw new IllegalArgumentException();

        this.completionMonth = completionMonth;
    }

    //Completion day setter
    public void setCompletionDay(int completionDay) {
        if (completionDay < 0 || completionDay > 31)
            //Throw an error if completion day is not between 0 and 31
            throw new IllegalArgumentException();

        this.completionDay = completionDay;
    }

    //100 percent status setter
    public void setPercent100(String percent100) {
        if (!percent100.equals("") && !percent100.equals("Yes") && !percent100.equals("No"))
            //Throw an error if 100% status is not blank, yes, or no
            throw new IllegalArgumentException();

        this.percent100 = percent100;
    }

    //Rating getter
    public int getRating() {
        return this.rating;
    }

    //Short status getter
    public String getShortStatus() {
        return this.shortStatus;
    }

    //Completion year getter
    public int getCompletionYear() {
        return this.completionYear;
    }

    //Completion month getter
    public int getCompletionMonth() {
        return this.completionMonth;
    }

    //Completion day getter
    public int getCompletionDay() {
        return this.completionDay;
    }

    //100 Percent status getter
    public String getPercent100() {
        return this.percent100;
    }
}
