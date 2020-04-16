package com.squadro.touricity.database.query.locationQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RouteSuggestionQuery extends SelectionQuery {

    private ArrayList<String> routeIdList = new ArrayList<>(5);

    @Override
    public String getQuery() {
        return "SELECT ROUTE_ID FROM DB_SUGGESTED LIMIT 5";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        ResultSet rs = result.getResultSet();
        if(result.isSuccessfull()){
            do{
                routeIdList.add(rs.getString("route_id"));
            }while(rs.next());
        }
        return false;
    }

    public ArrayList<String> getRouteIdList() {
        return routeIdList;
    }
}
