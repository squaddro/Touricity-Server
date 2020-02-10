package com.squadro.touricity.message.types.data;

public interface IFilter extends IDataType {
    String getCity_name();
    double getScore();
    int getExpense();
    int getDuration();
    int getPath_type();

    void setCity_name(String city_name);
    void setScore(double score);
    void setExpense(int expense);
    void setDuration(int duration);
    void setPath_type(int path_type);
}
