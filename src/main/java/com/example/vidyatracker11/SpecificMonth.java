package com.example.vidyatracker11;

public class SpecificMonth {
    //Fields
    private final int month;
    private final int year;

    private final String title;

    static final String[] months = {"N/A", "January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December"};  //Month strings

    public SpecificMonth(int month, int year) {
        this.month = month;
        this.year = year;
        if(month != 0)
            title = months[month] + ", " + year;
        else
            title = "0";
    }

    public int getNameValue(){
        if(month != 0)
            return (year * 100) + month;
        else
            return 0;
    }

    @Override
    public String toString() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

}
