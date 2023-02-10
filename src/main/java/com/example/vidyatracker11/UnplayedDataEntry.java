package com.example.vidyatracker11;

//Used by tableviews in the stats screen
public class UnplayedDataEntry extends DataEntry{

    private String totalHours;

    //Total hours getter
    public String getTotalHours() {
        return totalHours;
    }

    //Total hours setter
    public void setTotalHours(String totalHours) {
        this.totalHours = totalHours;
    }
}
