package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoesStopExists extends SelectionQuery {

    private final String stop_id;
    private boolean doesStopExists;

    public DoesStopExists(String stop_id) {
        this.stop_id = stop_id;
    }

    @Override
    public String getQuery() {
        return "SELECT COUNT(*) FROM db_stop WHERE stop_id = '" + stop_id + "'";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        ResultSet rs = result.getResultSet();
        if(!rs.next())
            doesStopExists = false;
        else
            doesStopExists = true;
        return false;
    }

    public boolean getDoesStopExists(){
        return doesStopExists;
    }
}
