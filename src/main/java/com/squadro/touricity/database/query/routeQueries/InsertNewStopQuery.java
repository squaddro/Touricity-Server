package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.InsertionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.Stop;

import java.sql.SQLException;

public class InsertNewStopQuery extends InsertionQuery {

    private final Stop stop;
    private final String newUUID;

    public InsertNewStopQuery(String newUUID, Stop stop) {
        this.stop = stop;
        this.newUUID = newUUID;
    }

    @Override
    public String getQuery() {
        return "INSERT INTO DB_stop(location_id, stop_id) VALUES(" + "'" + stop.getLocation_id() + "','" + newUUID + "')";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        return false;
    }
}
