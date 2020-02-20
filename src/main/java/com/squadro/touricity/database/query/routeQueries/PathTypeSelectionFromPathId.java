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
        return "SELECT path_type FROM db_path WHERE path_id = '" + path_id + "'";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        if(result.getResultSet().next()){
            pathType.set(result.getResultSet().getInt("path_type"));
        }
        return false;
    }

    public int getPathType(){
        return pathType.get();
    }
}
