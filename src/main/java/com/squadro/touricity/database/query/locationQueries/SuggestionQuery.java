package com.squadro.touricity.database.query.locationQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.BoundPoints;
import com.squadro.touricity.message.types.data.Location;
import jdk.nashorn.internal.objects.annotations.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SuggestionQuery extends SelectionQuery {

    private ArrayList<Location> locationList = new ArrayList<>(5);

    private double maxLng;
    private double minLng;
    private double maxLat;
    private double minLat;

    public SuggestionQuery(BoundPoints bp){
        this.maxLat = bp.getMaxLat();
        this.maxLng = bp.getMaxLng();
        this.minLat = bp.getMinLat();
        this.minLng = bp.getMinLng();
    }


    @Override
    public String getQuery() {
        return "SELECT LATITUDE, LONGITUDE, DB_STOP.LOCATION_ID, COUNT(*) AS counted " +
                "FROM DB_STOP " +
                "INNER JOIN DB_LOCATION ON DB_STOP.LOCATION_ID = DB_LOCATION.LOCATION_ID " +
                "WHERE DB_LOCATION.LATITUDE >= " + minLat + " AND DB_LOCATION.LONGITUDE >= " + minLng + " AND DB_LOCATION.LATITUDE <= " + maxLat + " AND DB_LOCATION.LONGITUDE <= + " + maxLng +
                " GROUP BY DB_STOP.LOCATION_ID, DB_LOCATION.LATITUDE, DB_LOCATION.LONGITUDE " +
                "ORDER BY COUNTED DESC, LOCATION_ID " +
                "LIMIT 5";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        ResultSet rs = result.getResultSet();

        if(result.isSuccessfull()){
            do{
                locationList.add(new Location(rs.getString("location_id"), rs.getDouble("latitude"), rs.getDouble("longitude")));
            }while(rs.next());
        }
        return false;
    }

    public ArrayList<Location> getLocationList(){
        return this.locationList;
    }
}
