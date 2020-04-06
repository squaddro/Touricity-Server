package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.InsertionQuery;
import com.squadro.touricity.database.query.userQueries.UserCheckQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.IEntry;
import com.squadro.touricity.message.types.data.Route;
import org.apache.catalina.User;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class InsertNewRouteQuery extends InsertionQuery{

    private final String username;
    private AtomicReference<String> creator = new AtomicReference<>();
    private AtomicReference<String> route_id = new AtomicReference<>();
    private final IEntry[] entries;
    private final String city_id;
    private final String title;
    private final int privacy;

    public InsertNewRouteQuery(Route route, String username) {
        this.route_id.set(route.getRoute_id());
        this.entries = route.getEntries();
        this.creator.set(route.getCreator());
        this.city_id = "111111111111111111111111111111111111";
        this.title = route.getTitle();
        this.privacy = route.getPrivacy();
        this.username = username;
    }

    @Override
    public String getQuery() {
        DoesRouteExists doesRouteExists = new DoesRouteExists(route_id.get());
        doesRouteExists.execute();

        if(doesRouteExists.getDoesRouteExists()){
            return "UPDATE db_route SET creator = '" + creator + "', route_id = '" + route_id.get() + "', city_id = '" + city_id + "', title = '" + title + "', route_desc = 'dummy desc', privacy =" + privacy;
        }
        else{
            UserCheckQuery userCheckQuery = new UserCheckQuery(username);
            userCheckQuery.execute();
            creator.set(userCheckQuery.getAccountId());

            String newID = UUID.randomUUID().toString();
            route_id.set(newID);
            return "INSERT INTO db_route(creator,route_id,city_id,title,route_desc,privacy) VALUES('" + creator + "','" + newID + "','" + city_id + "','" + title + "'," + "'desccc' ," + privacy + ")";
        }
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {

        for(int i = 0; i < entries.length; i++) {
            InsertNewEntryQuery insertNewEntryQuery = new InsertNewEntryQuery(route_id.get(), entries[i],i);
            insertNewEntryQuery.execute();
            entries[i] = insertNewEntryQuery.getEntry();
        }
        return false;
    }

    public Route getRoute(){
        return new Route(creator.get(), route_id.get(), entries, city_id, title, privacy);
    }
}
