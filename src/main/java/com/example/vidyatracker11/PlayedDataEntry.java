package com.example.vidyatracker11;

//Used by tableviews in the stats screen
public class PlayedDataEntry extends DataEntry{

    private String averageRating;

    //averageRating getter
    public String getAverageRating() {
        return averageRating;
    }

    //averageRating setter
    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }
}