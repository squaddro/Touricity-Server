package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.Path;
import com.squadro.touricity.message.types.data.Stop;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PathListSelectionFromRouteId extends SelectionQuery {

    private final String route_id;
    private final List<Path> list;

    public PathListSelectionFromRouteId(String route_id) {
        this.route_id = route_id;
        this.list = new ArrayList<>();
    }

    @Override
    public String getQuery() { //TODO: select Entry_id
        return "SELECT PATH_ID,EXPENSE,DURATION,COMMENT_DESC,POINTER FROM DB_ENTRY WHERE ROUTE_ID = " + route_id + "AND PATH_ID IS NOT NULL ORDER BY POINTER ASC";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        ResultSet rs = result.getResultSet();
        Path path;

        while (rs.next()) {
            path = new Path();
            path.setPath_id(rs.getString("PATH_ID"));
            path.setExpense(rs.getInt("EXPENSE"));
            path.setDuration(rs.getInt("DURATION"));
            path.setComment(rs.getString("COMMENT_DESC"));
            path.setIndex(rs.getInt("POINTER"));

            list.add(path);
        }
        return false;
    }
    public List<Path> getList() {
        return list;
    }
}
