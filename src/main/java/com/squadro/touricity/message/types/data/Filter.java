package com.squadro.touricity.message.types.data;

public class Filter implements IFilter {

    private String city_name;
    private double score;
    private int expense;
    private int duration;
    private int path_type;

    public String getCity_name() {
        return city_name;
    }

    public double getScore() {
        return score;
    }

    public int getExpense() {
        return expense;
    }

    public int getDuration() {
        return duration;
    }

    public int getPath_type() {
        return path_type;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPath_type(int path_type) {
        this.path_type = path_type;
    }
}