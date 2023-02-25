package com.example.vidyatracker11;

//Used by tableviews in the stats screen
public class PlayedDataEntry extends DataEntry{

    private int totalRating = 0;
    private int ratingCount = 0;
    private Double averageRating;

    //averageRating getter
    public Double getAverageRating() {
        return averageRating;
    }

    //averageRating setter
    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    //totalRating getter
    public int getTotalRating() {
        return totalRating;
    }

    //ratingCount getter
    public int getRatingCount() {
        return ratingCount;
    }

    //totalRating setter
    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    //ratingCount setter
    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }
}