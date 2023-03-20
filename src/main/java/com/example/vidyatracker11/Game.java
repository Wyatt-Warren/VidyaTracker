package com.example.vidyatracker11;

//Superclass for played game and unplayed game since they share many data fields.
public abstract class Game {
    //Fields
    protected String status;        //Status of game
    private String title;           //Title of game
    private String platform;        //Platform of game
    private String genre;           //Genre of game
    private int releaseYear;        //Year game was released
    private int releaseMonth;       //Month game was released
    private int releaseDay;         //Day game was released
    private String franchise = "";  //Franchise of game

    public Game(String title, String status, String platform,
                String genre, int releaseYear, int releaseMonth,
                int releaseDay) {
        //Set global variables
        this.status = status;
        this.title = title;
        this.platform = platform;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.releaseMonth = releaseMonth;
        this.releaseDay = releaseDay;

        if (!GameLists.platformList.contains(platform))
            //Throw error if platform entered was not valid
            throw new IllegalArgumentException();

        if (!GameLists.genreList.contains(genre))
            //Throw error if the genre entered was not valid
            throw new IllegalArgumentException();

        if (releaseYear < 0)
            //Throw error if the release year is negative
            throw new IllegalArgumentException();

        if (releaseMonth < 0 || releaseMonth > 12)
            //Throw error if the month is not between 0 and 12
            throw new IllegalArgumentException();

        if (releaseDay < 0 || releaseDay > 31)
            //Throw error if the day is not between 0 and 31
            throw new IllegalArgumentException();
    }

    //Status setter
    public abstract void setStatus(String paramString);

    //Franchise setter
    public void setFranchise(String franchise) {
        this.franchise = franchise;
    }

    //Title setter
    public void setTitle(String title) {
        this.title = title;
    }

    //Platform setter
    public void setPlatform(String platform) {
        if (!GameLists.platformList.contains(platform))
            //Throw error if platform is not in list
            throw new IllegalArgumentException();

        this.platform = platform;
    }

    //Genre setter
    public void setGenre(String genre) {
        if (!GameLists.genreList.contains(genre))
            //Throw error if genre is not in list
            throw new IllegalArgumentException();

        this.genre = genre;
    }

    //Release year setter
    public void setReleaseYear(int releaseYear) {
        if (releaseYear < 0)
            //Throw error if the release year is negative
            throw new IllegalArgumentException();

        this.releaseYear = releaseYear;
    }

    //Release month setter
    public void setReleaseMonth(int releaseMonth) {
        if (releaseMonth < 0 || releaseMonth > 12)
            //Throw error if the month is not between 0 and 12
            throw new IllegalArgumentException();

        this.releaseMonth = releaseMonth;
    }

    //Release day setter
    public void setReleaseDay(int releaseDay) {
        if (releaseDay < 0 || releaseDay > 31)
            //Throw error if the day is not between 0 and 31
            throw new IllegalArgumentException();

        this.releaseDay = releaseDay;
    }

    //Status getter
    public String getStatus() {
        return status;
    }

    //Title getter
    public String getTitle() {
        return title;
    }

    //Platform getter
    public String getPlatform() {
        return platform;
    }

    //Genre getter
    public String getGenre() {
        return genre;
    }

    //Release year getter
    public int getReleaseYear() {
        return releaseYear;
    }

    //Release month getter
    public int getReleaseMonth() {
        return releaseMonth;
    }

    //Release day getter
    public int getReleaseDay() {
        return releaseDay;
    }

    //Franchise getter
    public String getFranchise() {
        return franchise;
    }

    @Override
    public String toString() {
        return title;
    }
}