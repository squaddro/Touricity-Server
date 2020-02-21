package com.squadro.touricity.database.query.locationQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.Location;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class GetLocationInfoQuery extends SelectionQuery {

	final AtomicReference<String> city_id = new AtomicReference<>();
	final AtomicReference<Double> latitude = new AtomicReference<>();
	final AtomicReference<Double> longitude = new AtomicReference<>();
	private final String location_id;


	public GetLocationInfoQuery(String location_id) {
		this.location_id = location_id;
	}

	@Override
	public String getQuery() {
		return "SELECT * FROM DB_LOCATION WHERE LOCATION_ID = '" + location_id + "'";
	}

	@Override
	public boolean onResult(QueryResult result) throws SQLException {
		if(result.isSuccessfull()){
			city_id.set(result.getResultSet().getString("city_id"));
			latitude.set(result.getResultSet().getDouble("latitude"));
			longitude.set(result.getResultSet().getDouble("longitude"));		}
		return false;
	}

	public Location getLocation(){
		Location location = new Location(location_id, city_id.get(), latitude.get(), longitude.get());
		return location;
	}
}
