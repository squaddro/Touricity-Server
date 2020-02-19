package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.DeletionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.SQLException;

public class DeleteRouteQuery extends DeletionQuery {

    private final String route_id;

    private boolean isSuccessfull;

    public DeleteRouteQuery(String route_id) {
        this.route_id = route_id;
        isSuccessfull = false;
    }

    @Override
    public String getQuery() {
        return "DELETE FROM DB_ROUTE WHERE ROUTE_ID = '" + route_id + "'";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        isSuccessfull = result.isSuccessfull();
        return false;
    }
    public boolean isSuccessfull() {
        return isSuccessfull;
    }

}
