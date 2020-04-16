package com.squadro.touricity.message.types.data;

import java.util.ArrayList;

public class Suggestion {
    private ArrayList<Location> locationList;

    public Suggestion(ArrayList<Location> locations){
        this.locationList = locations;
    }

    public ArrayList<Location> getLocationList() {
        return locationList;
    }
}
