package com.squadro.touricity.message.types;

import com.squadro.touricity.message.types.data.Location;

import java.util.ArrayList;
import java.util.List;

public class Suggestion {
    private ArrayList<Location> locationList;

    public Suggestion(ArrayList<Location> locations){
        this.locationList = locations;
    }

    public ArrayList<Location> getLocationList() {
        return locationList;
    }
}
