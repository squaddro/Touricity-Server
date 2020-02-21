package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class CityIdSelectionFromRouteId extends SelectionQuery {

    private final AtomicReference<String> cityId = new AtomicReference<>();
    private final String route_id;

    public CityIdSelectionFromRouteId(String route_id) {
        this.route_id = route_id;
    }

    @Override
    public String getQuery() {
        return "SELECT city_id FROM DB_ROUTE WHERE route_id = '" + route_id + "'";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        if(result.isSuccessfull()){
            cityId.set(result.getResultSet().getString("city_id"));
        }
        return false;
    }

    public String getCityId(){
        return cityId.get();
    }
}
