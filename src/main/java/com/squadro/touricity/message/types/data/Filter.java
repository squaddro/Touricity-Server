package com.squadro.touricity.message.types.data;

public class Filter {

    private String city;
    private double minRate;
    private int averageCost;
    private int duration;
    private int transportation;

    public String getCity() {
        return city;
    }

    public double getMinRate() {
        return minRate;
    }

    public int getAverageCost() {
        return averageCost;
    }

    public int getDuration() {
        return duration;
    }

    public int getTransportation() {
        return transportation;
    }
}