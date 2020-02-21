package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.InsertionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.Stop;

import java.sql.SQLException;

public class InsertNewStopQuery extends InsertionQuery {

    private final String entryQuery;

    public InsertNewStopQuery(String entryQuery) {
        this.entryQuery = entryQuery;
    }

    @Override
    public String getQuery() {
        return entryQuery;
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        return false;
    }
}
