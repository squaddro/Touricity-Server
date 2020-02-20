package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class CreatorSelectionFromRouteId extends SelectionQuery {

    private final AtomicReference<String> creator = new AtomicReference<>();
    private final String route_id;
    private final CountDownLatch countDownLatch;

    public CreatorSelectionFromRouteId(String route_id) {
        this.route_id = route_id;
        countDownLatch = new CountDownLatch(1);
    }

    @Override
    public String getQuery() {
        return "SELECT creator FROM db_route WHERE route_id = '" + route_id + "'";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        if(result.getResultSet().next()){
            creator.set(result.getResultSet().getString("creator"));
            countDownLatch.countDown();
        }
        return false;
    }

    public String getCreator(){
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return creator.get();
    }
}
