package com.example.vidyatracker11;

public class SpecificMonth {
    //Fields
    int month;
    int year;
    String title;

    static final String[] months = {"N/A", "January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December"};  //Month strings

    public SpecificMonth(int month, int year) {
        this.month = month;
        this.year = year;
        title = months[month] + ", " + year;
    }

    @Override
    public String toString() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }
}
