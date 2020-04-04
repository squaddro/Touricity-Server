package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class LikeIdSelectionFromRouteId extends SelectionQuery {

    private final String route_id;
    private final List<String> list;
    public boolean isSuccessfull;


    public LikeIdSelectionFromRouteId(String route_id) {
        this.route_id = route_id;
        list = new ArrayList<>();
    }

    @Override
    public String getQuery() {
        return "SELECT like_id FROM DB_ROUTE_LIKE WHERE route_id = '" + route_id + "'";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        if(result.isSuccessfull()){
            do{
                list.add(result.getResultSet().getString("like_id"));
            }while(result.getResultSet().next());
            isSuccessfull = true;
            return true;
        }
        return false;
    }
    public List<String> getList() {
        return list;
    }

}
