package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class CreatorSelectionFromRouteId extends SelectionQuery {

    private final AtomicReference<String> creator = new AtomicReference<>();
    private final String route_id;

    public CreatorSelectionFromRouteId(String route_id) {
        this.route_id = route_id;
    }

    @Override
    public String getQuery() {
        return "SELECT creator FROM db_route WHERE route_id = '" + route_id + "'";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        if(result.getResultSet().next()){
            creator.set(result.getResultSet().getString("creator"));
        }
        return false;
    }

    public String getCreator(){
        return creator.get();
    }

}
