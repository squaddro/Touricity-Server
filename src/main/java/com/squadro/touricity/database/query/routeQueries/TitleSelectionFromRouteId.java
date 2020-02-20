package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class TitleSelectionFromRouteId extends SelectionQuery {

    private final AtomicReference<String> title = new AtomicReference<>();
    private final String route_id;

    public TitleSelectionFromRouteId(String route_id) {
        this.route_id = route_id;
    }

    @Override
    public String getQuery() {
        return "SELECT title FROM db_route WHERE route_id = '" + route_id + "'";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        if(result.getResultSet().next()){
            title.set(result.getResultSet().getString("title"));
        }
        return false;
    }

    public String getTitle(){
        return title.get();
    }
}
