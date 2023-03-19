package com.example.vidyatracker11;

//Used to store items in tables in StatsScreen
public class DataEntry {
    //Fields
    protected String name;      //Name of data
    protected int intName;      //Name if data is an int
    protected int count;        //Count of data
    protected Double percent;   //Percentage of data in its list

    //count getter
    public int getCount() {
        return count;
    }

    //count setter
    public void setCount(int count) {
        this.count = count;
    }

    //name getter
    public String getName() {
        return name;
    }

    //name setter
    public void setName(String name) {
        this.name = name;
    }

    //percent getter
    public Double getPercent() {
        return percent;
    }

    //percent setter
    public void setPercent(Double percent) {
        this.percent = percent;
    }

    //intName getter
    public int getIntName() {
        return intName;
    }

    //intName setter
    public void setIntName(int intName) {
        this.intName = intName;
    }
}
