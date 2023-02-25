package com.example.vidyatracker11;

public class DataEntry {
    protected String name;

    protected int count;

    protected String percent;

    //Count getter
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
    public String getPercent() {
        return percent;
    }

    //percent setter
    public void setPercent(String percent) {
        this.percent = percent;
    }
}
