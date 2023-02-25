package com.example.vidyatracker11;

//Superclass for played game and unplayed game since they share many data fields.
public abstract class Game {

    protected String status;

    private String title;

    private String platform;

    private String genre;

    private int releaseYear;

    private int releaseMonth;

    private int releaseDay;

    private String franchise = "";

    //Constructor
    public Game(String title, String status, String platform,
                String genre, int releaseYear, int releaseMonth,
                int releaseDay) {
        this.status = status;
        this.title = title;
        this.platform = platform;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.releaseMonth = releaseMonth;
        this.releaseDay = releaseDay;
        boolean inList = false;
        for (int i = 0; i < GameLists.platformList.size(); i++) {
            if (platform.equals(GameLists.platformList.get(i))) {
                inList = true;
                break;
            }
        }
        if (!inList)
            throw new IllegalArgumentException();

        inList = false;
        for (int i = 0; i < GameLists.genreList.size(); i++) {
            if (genre.equals(GameLists.genreList.get(i))) {
                inList = true;
                break;
            }
        }
        if (!inList)
            throw new IllegalArgumentException();
        if (releaseYear < 0)
            throw new IllegalArgumentException();
        if (releaseMonth < 0 || releaseMonth > 12)
            throw new IllegalArgumentException();
        if (releaseDay < 0 || releaseDay > 31)
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
        boolean inList = false;
        for (int i = 0; i < GameLists.platformList.size(); i++) {
            if (platform.equals(GameLists.platformList.get(i))) {
                inList = true;
                break;
            }
        }
        if (!inList)
            throw new IllegalArgumentException();
        this.platform = platform;
    }

    //Genre setter
    public void setGenre(String genre) {
        boolean inList = false;
        for (int i = 0; i < GameLists.genreList.size(); i++) {
            if (genre.equals(GameLists.genreList.get(i))) {
                inList = true;
                break;
            }
        }
        if (!inList)
            throw new IllegalArgumentException();
        this.genre = genre;
    }

    //Release year setter
    public void setReleaseYear(int releaseYear) {
        if (releaseYear < 0)
            throw new IllegalArgumentException();
        this.releaseYear = releaseYear;
    }

    //Release month setter
    public void setReleaseMonth(int releaseMonth) {
        if (releaseMonth < 0 || releaseMonth > 12)
            throw new IllegalArgumentException();
        this.releaseMonth = releaseMonth;
    }

    //Release day setter
    public void setReleaseDay(int releaseDay) {
        if (releaseDay < 0 || releaseDay > 31)
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
}