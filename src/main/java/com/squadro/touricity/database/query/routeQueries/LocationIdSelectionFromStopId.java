package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.Stop;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class LocationIdSelectionFromStopId extends SelectionQuery {

    private final String stop_id;
    private final AtomicReference<String> location_id = new AtomicReference<>();

    public LocationIdSelectionFromStopId(String stop_id) {
        this.stop_id = stop_id;
    }

    @Override
    public String getQuery() {
        return "SELECT location_id FROM db_stop WHERE stop_id = '" + stop_id + "'";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        if(result.getResultSet().next()){
            location_id.set(result.getResultSet().getString("location_id"));
        }
        return false;
    }

    public String getLocationId(){
        return location_id.get();
    }
}
