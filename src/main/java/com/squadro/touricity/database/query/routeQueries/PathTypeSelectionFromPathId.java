package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class PathTypeSelectionFromPathId extends SelectionQuery {

    private final String path_id;
    private final AtomicInteger pathType = new AtomicInteger();

    public PathTypeSelectionFromPathId(String path_id) {
        this.path_id = path_id;
    }

    @Override
    public String getQuery() {
        return "SELECT PATH_TYPE FROM DB_PATH WHERE PATH_ID = " + path_id;
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        ResultSet rs = result.getResultSet();
        pathType.set(rs.getInt("path_type"));
        return false;
    }

    public int getPathType(){
        return pathType.get();
    }
}
