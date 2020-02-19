package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.InsertionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.IEntry;
import com.squadro.touricity.message.types.data.Route;

import java.sql.SQLException;
import java.util.UUID;

public class InsertNewRouteQuery extends InsertionQuery{

    private final String creator;
    private final String route_id;
    private final IEntry[] entries;
    private final String city_id;
    private final String title;
    private final int privacy;

    public InsertNewRouteQuery(String route_id, String creator, IEntry[] entries, String city_id, String title, int privacy) {
        this.route_id = route_id;
        this.creator = creator;
        this.entries = entries;
        this.city_id = city_id;
        this.title = title;
        this.privacy = privacy;
    }

    @Override
    public String getQuery() {

        String query = null;
        if(route_id == null)
            query = "INSERT INTO db_route(creator,route_id,city_id,title,route_desc,privacy) VALUES('" + creator + "','" + UUID.randomUUID().toString() + "','" + city_id + "','" + title + "'," + "'desccc' ," + privacy + ")";

        else{
            DoesRouteExists doesRouteExists = new DoesRouteExists(route_id);
            doesRouteExists.execute();

            if(doesRouteExists.getDoesRouteExists()){
                query = "UPDATE db_route SET CREATOR = '" + creator + "', ROUTE_ID = '" + UUID.randomUUID().toString() + "', CITY_ID = '" + city_id + "', TITLE = '" + title + "', ROUTE_DESC = 'dummy desc', PRIVACY =" + privacy;
            }
        }

        return query;
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
        return new Route(creator, route_id, entries, city_id, title, privacy);
    }
}
