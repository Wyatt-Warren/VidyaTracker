package com.example.vidyatracker11;

//Used by tableviews in the stats screen
public class UnplayedDataEntry extends DataEntry{
    //Fields
    private Double totalHours = 0.0;    //Total hours field

    //Total hours getter
    public Double getTotalHours() {
        return totalHours;
    }

    //Total hours setter
    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }
}
