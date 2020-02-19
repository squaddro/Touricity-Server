package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class PrivacySelectionFromRouteId extends SelectionQuery {

    private final AtomicInteger privacy= new AtomicInteger();
    private final String route_id;

    public PrivacySelectionFromRouteId(String route_id) {
        this.route_id = route_id;
    }

    @Override
    public String getQuery() {
        return "SELECT PRIVACY FROM DB_ROUTE WHERE ROUTE_ID = '" + route_id + "'";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        privacy.set(result.getResultSet().getInt("privacy"));
        return false;
    }

    public int getPrivacy(){
        return privacy.get();
    }
}
