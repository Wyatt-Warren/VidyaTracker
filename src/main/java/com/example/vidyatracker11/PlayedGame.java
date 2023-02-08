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
                      int releaseDay) throws
            InvalidStatusException, InvalidPlatformException, InvalidGenreException,
            InvalidYearException, InvalidMonthException, InvalidDayException {
        super(title, status, platform, genre, releaseYear, releaseMonth, releaseDay);
        if (!status.equals("Playing") && !status.equals("Completed") && !status.equals("On Hold"))
            throw new InvalidStatusException();
        if (getReleaseYear() < 0)
            throw new InvalidYearException();
        if (getReleaseMonth() < 0 || getReleaseMonth() > 12)
            throw new InvalidMonthException();
        if (getReleaseDay() < 0 || getReleaseDay() > 31)
            throw new InvalidDayException();
    }

    //Status setter
    public void setStatus(String status) throws InvalidStatusException {
        if (!status.equals("Playing") && !status.equals("Completed") && !status.equals("On Hold"))
            throw new InvalidStatusException();
        this.status = status;
    }

    //Rating setter
    public void setRating(int rating) throws InvalidRatingException {
        if (0 > rating || rating >= 11)
            throw new InvalidRatingException();
        this.rating = rating;
    }

    //Short status setter
    public void setIsItShort(String isItShort) throws InvalidShortStatusException {
        if (!isItShort.equals("") && !isItShort.equals("Yes") && !isItShort.equals("No"))
            throw new InvalidShortStatusException();
        this.isItShort = isItShort;
    }

    //Completion Year setter
    public void setCompletionYear(int completionYear) throws InvalidYearException {
        if (completionYear < 0)
            throw new InvalidYearException();
        this.completionYear = completionYear;
    }

    //Completion month setter
    public void setCompletionMonth(int completionMonth) throws InvalidMonthException {
        if (completionMonth < 0 || completionMonth > 12)
            throw new InvalidMonthException();
        this.completionMonth = completionMonth;
    }

    //Completion day setter
    public void setCompletionDay(int completionDay) throws InvalidDayException {
        if (completionDay < 0 || completionDay > 31)
            throw new InvalidDayException();
        this.completionDay = completionDay;
    }

    //100 percent status setter
    public void setPercent100(String percent100) throws InvalidPercentException {
        if (!percent100.equals("") && !percent100.equals("Yes") && !percent100.equals("No"))
            throw new InvalidPercentException();
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
