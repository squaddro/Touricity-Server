package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.InsertionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.IEntry;
import com.squadro.touricity.message.types.data.Route;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class InsertNewRouteQuery extends InsertionQuery{

    private final String creator;
    private AtomicReference<String> route_id = new AtomicReference<>();
    private final IEntry[] entries;
    private final String city_id;
    private final String title;
    private final int privacy;

    public InsertNewRouteQuery(String route_id, String creator, IEntry[] entries, String city_id, String title, int privacy) {
        this.route_id.set(route_id);
        this.creator = creator;
        this.entries = entries;
        this.city_id = city_id;
        this.title = title;
        this.privacy = privacy;
    }

    @Override
    public String getQuery() {

        String query = null;
        if(route_id.get() == null){
            String newID = UUID.randomUUID().toString();
            query = "INSERT INTO db_route(creator,route_id,city_id,title,route_desc,privacy) VALUES('" + creator + "','" + newID + "','" + city_id + "','" + title + "'," + "'desccc' ," + privacy + ")";
            route_id.set(newID);
        }

        else{
            DoesRouteExists doesRouteExists = new DoesRouteExists(route_id.get());
            doesRouteExists.execute();

            if(doesRouteExists.getDoesRouteExists()){
                query = "UPDATE db_route SET creator = '" + creator + "', route_id = '" + route_id.get() + "', city_id = '" + city_id + "', title = '" + title + "', route_desc = 'dummy desc', privacy =" + privacy;
            }
        }

        return query;
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {

        for(int i = 0; i < entries.length; i++) {
            InsertNewEntryQuery insertNewEntryQuery = new InsertNewEntryQuery(route_id.get(), entries[i]);
            insertNewEntryQuery.execute();
            entries[i] = insertNewEntryQuery.getEntry();
        }
        return false;
    }

    public Route getRoute(){
        return new Route(creator, route_id.get(), entries, city_id, title, privacy);
    }
}
