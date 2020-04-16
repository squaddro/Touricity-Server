package com.squadro.touricity.message.types.data;

import java.util.ArrayList;
import java.util.List;

public class RouteSuggestion {

    private List<RouteLike> routeList;

    public RouteSuggestion(List<RouteLike> routeList){
        this.routeList = new ArrayList<>(5);
        this.routeList = routeList;
    }

    public List<RouteLike> getRouteList() {
        return routeList;
    }
}
