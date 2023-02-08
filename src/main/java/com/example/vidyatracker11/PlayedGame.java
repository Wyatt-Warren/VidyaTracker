package com.example.vidyatracker11;

//A played game stored in the played game list.
public class PlayedGame extends Game {

    private int rating;
    private String isItShort = "";
    private int completionYear = 0;
    private int completionMonth = 0;
    private int completionDay = 0;
    private String percent100 = "";

    //Constructor
    public PlayedGame(String title, String status, String platform,
                      String genre, int releaseYear, int releaseMonth,
                      int releaseDay) {
        super(title, status, platform, genre, releaseYear, releaseMonth, releaseDay);
        if (!status.equals("Playing") && !status.equals("Completed") && !status.equals("On Hold"))
            throw new IllegalArgumentException();
        if (getReleaseYear() < 0)
            throw new IllegalArgumentException();
        if (getReleaseMonth() < 0 || getReleaseMonth() > 12)
            throw new IllegalArgumentException();
        if (getReleaseDay() < 0 || getReleaseDay() > 31)
            throw new IllegalArgumentException();
    }

    //Status setter
    public void setStatus(String status) {
        if (!status.equals("Playing") && !status.equals("Completed") && !status.equals("On Hold"))
            throw new IllegalArgumentException();
        this.status = status;
    }

    //Rating setter
    public void setRating(int rating) {
        if (0 > rating || rating >= 11)
            throw new IllegalArgumentException();
        this.rating = rating;
    }

    //Short status setter
    public void setIsItShort(String isItShort) {
        if (!isItShort.equals("") && !isItShort.equals("Yes") && !isItShort.equals("No"))
            throw new IllegalArgumentException();
        this.isItShort = isItShort;
    }

    //Completion Year setter
    public void setCompletionYear(int completionYear) {
        if (completionYear < 0)
            throw new IllegalArgumentException();
        this.completionYear = completionYear;
    }

    //Completion month setter
    public void setCompletionMonth(int completionMonth) {
        if (completionMonth < 0 || completionMonth > 12)
            throw new IllegalArgumentException();
        this.completionMonth = completionMonth;
    }

    //Completion day setter
    public void setCompletionDay(int completionDay) {
        if (completionDay < 0 || completionDay > 31)
            throw new IllegalArgumentException();
        this.completionDay = completionDay;
    }

    //100 percent status setter
    public void setPercent100(String percent100) {
        if (!percent100.equals("") && !percent100.equals("Yes") && !percent100.equals("No"))
            throw new IllegalArgumentException();
        this.percent100 = percent100;
    }

    //Rating getter
    public int getRating() {
        return this.rating;
    }

    //Short status getter
    public String getIsItShort() {
        return this.isItShort;
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
