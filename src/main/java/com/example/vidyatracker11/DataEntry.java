package com.example.vidyatracker11;

public class DataEntry {
    protected String name;

    protected int intName;

    protected int count;

    protected Double percent;

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
    public Double getPercent() {
        return percent;
    }

    //percent setter
    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public int getIntName() {
        return intName;
    }

    public void setIntName(int intName) {
        this.intName = intName;
    }
}
