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
        return "SELECT LOCATION_ID FROM DB_STOP WHERE STOP_ID = '" + stop_id + "'";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        ResultSet rs = result.getResultSet();
        location_id.set(rs.getString("location_id"));
        return false;
    }

    public String getLocationId(){
        return location_id.get();
    }
}
