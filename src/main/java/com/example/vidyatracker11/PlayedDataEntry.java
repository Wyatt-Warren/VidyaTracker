package com.example.vidyatracker11;

//Used to store PlayedGames in tables in StatsScreen
public class PlayedDataEntry extends DataEntry{
    //Fields
    private int totalRating = 0;    //Total of all ratings used to find the average
    private int ratingCount = 0;    //Amount of games with a rating
    private Double averageRating;   //Average rating field

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