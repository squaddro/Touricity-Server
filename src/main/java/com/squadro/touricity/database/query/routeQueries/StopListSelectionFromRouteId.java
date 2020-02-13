package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.Entry;
import com.squadro.touricity.message.types.data.IEntry;
import com.squadro.touricity.message.types.data.Stop;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StopListSelectionFromRouteId extends SelectionQuery {

    private final String route_id;
    private final List<Stop> list;

    public StopListSelectionFromRouteId(String route_id) {
        this.route_id = route_id;
        this.list = new ArrayList<>();
    }

    @Override
    public String getQuery() { //TODO: select Entry_id
        return "SELECT STOP_ID,EXPENSE,DURATION,COMMENT_DESC,POINTER FROM DB_ENTRY WHERE ROUTE_ID = " + route_id + "AND STOP_ID IS NOT NULL ORDER BY POINTER ASC";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        ResultSet rs = result.getResultSet();
        Stop stop;

        while (rs.next()) {
            stop = new Stop();
            stop.setStop_id(rs.getString("STOP_ID"));
            stop.setExpense(rs.getInt("EXPENSE"));
            stop.setDuration(rs.getInt("DURATION"));
            stop.setComment(rs.getString("COMMENT_DESC"));
            stop.setIndex(rs.getInt("POINTER"));

            list.add(stop);
        }
        return false;
    }

    public List<Stop> getList() {
        return list;
    }
}
