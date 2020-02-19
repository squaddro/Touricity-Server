package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoesRouteExists extends SelectionQuery {

    private final String route_id;
    private boolean doesRouteExists;

    public DoesRouteExists(String route_id) {
        this.route_id = route_id;
    }

    @Override
    public String getQuery() {
        return "SELECT COUNT(*) FROM db_route WHERE ROUTE_ID = '" + route_id + "'";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        ResultSet rs = result.getResultSet();
        if(rs.getRow() == 0)
            doesRouteExists = false;
        else
            doesRouteExists = true;
        return false;
    }

    public boolean getDoesRouteExists(){
        return doesRouteExists;
    }
}
