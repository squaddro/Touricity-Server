package com.squadro.touricity.message.types.data;

public class City implements ICity {
    private String cityId;
    private String cityName;

    public City() {

    }

    public City(String cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }


    public String getCity_Id() {
        return cityId;
    }

    public String getCity_Name() {
        return cityName;
    }

    public void setCity_Id(String cityId) {
        this.cityId = cityId;
    }

    public void setCity_Name(String cityName) {
        this.cityName = cityName;
    }
}
