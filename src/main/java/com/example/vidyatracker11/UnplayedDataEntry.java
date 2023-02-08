package com.example.vidyatracker11;

//Used by tableviews in the stats screen
public class UnplayedDataEntry {
    private String title;

    private int count;

    private String percent;

    private String totalHours;

    //Total hours getter
    public String getTotalHours() {
        return totalHours;
    }

    //Total hours setter
    public void setTotalHours(String totalHours) {
        this.totalHours = totalHours;
    }

    //Count getter
    public int getCount() {
        return count;
    }

    //Count setter
    public void setCount(int count) {
        this.count = count;
    }

    //Name getter
    public String getName() {
        return title;
    }

    //Name setter
    public void setName(String name) {
        this.title = name;
    }

    //Percent getter
    public String getPercent() {
        return percent;
    }

    //Percent setter
    public void setPercent(String percent) {
        this.percent = percent;
    }
}
