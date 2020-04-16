package com.squadro.touricity.message.types.data;

import java.util.ArrayList;

public class RouteSuggestion {

    private ArrayList<Route> routeList;

    public RouteSuggestion(){
        routeList = new ArrayList<>(5);
    }

    public ArrayList<Route> getRouteList() {
        return routeList;
    }
}
