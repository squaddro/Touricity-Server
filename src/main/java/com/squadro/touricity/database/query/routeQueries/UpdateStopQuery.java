package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.UpdateQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.Stop;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class UpdateStopQuery extends UpdateQuery {

    private final AtomicReference<Stop> stop = new AtomicReference<>();

    public UpdateStopQuery(Stop stop) {
        this.stop.set(stop);
    }

    @Override
    public String getQuery() {
        return "UPDATE DB_STOP SET LOCATION_ID ='" + this.stop.get().getLocation_id() + "' WHERE STOP_ID ='" + this.stop.get().getStop_id() + "'";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        return false;
    }
}
