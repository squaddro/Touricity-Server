package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoesPathExists extends SelectionQuery {

    private final String path_id;
    private boolean doesPathExists;

    public DoesPathExists(String path_id) {
        this.path_id = path_id;
    }

    @Override
    public String getQuery() {
        return "SELECT COUNT(*) FROM db_path WHERE path_id = '" + path_id + "'";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        ResultSet rs = result.getResultSet();
        if(!rs.next())
            doesPathExists = false;
        else
            doesPathExists = true;
        return false;
    }
    public boolean getDoesPathExists(){
        return doesPathExists;
    }
}
