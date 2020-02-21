package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.InsertionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.IPath;
import com.squadro.touricity.message.types.data.Path;
import com.squadro.touricity.message.types.data.Stop;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

public class InsertNewPathQuery extends InsertionQuery {

    private final String entryQuery;

    public InsertNewPathQuery(String entryQuery) {
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
