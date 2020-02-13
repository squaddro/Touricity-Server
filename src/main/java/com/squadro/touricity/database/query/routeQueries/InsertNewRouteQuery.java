package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.InsertionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.IEntry;
import com.squadro.touricity.message.types.data.Route;

import java.sql.SQLException;

public class InsertNewRouteQuery extends InsertionQuery{

    private final String creator;
    private final String route_id;
    private final IEntry[] entries;
    private final String cityId;
    private final String title;
    private final int privacy;

    public InsertNewRouteQuery(String route_id, String creator, IEntry[] entries, String cityId, String title, int privacy) {
        this.route_id = route_id;
        this.creator = creator;
        this.entries = entries;
        this.cityId = cityId;
        this.title = title;
        this.privacy = privacy;
    }

    @Override
    public String getQuery() {
        String insertionQuery = "INSERT INTO DB_ROUTE VALUES(" + creator + "," + route_id + "," + cityId + "," + title + "," + "NULL ," + privacy + ")";
        return insertionQuery;
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {

        for(int i = 0; i < entries.length; i++) {
            InsertNewEntryQuery insertNewEntryQuery = new InsertNewEntryQuery(route_id, entries[i]);
            insertNewEntryQuery.execute();
            entries[i] = insertNewEntryQuery.getEntry();
        }
        return false;
    }

    public Route getRoute(){
        return new Route(creator, route_id, entries, cityId, title, privacy);
    }
}