package com.squadro.touricity.database.query.locationQueries;

import com.squadro.touricity.database.query.InsertionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.SQLException;

public class InsertNewLocationQuery extends InsertionQuery {

	private final String location_id;
	private final double longitude;
	private final double latitude;

	private boolean isSuccessfull;

	public InsertNewLocationQuery(String location_id, double longitude, double latitude) {
		this.location_id = location_id;
		this.longitude = longitude;
		this.latitude = latitude;
		isSuccessfull = false;
	}

	@Override
	public String getQuery() {
		return "INSERT INTO DB_LOCATION VALUES('" + location_id + "',NULL," + latitude + "," + longitude + ")";
	}

	@Override
	public boolean onResult(QueryResult result) throws SQLException {
		isSuccessfull = result.isSuccessfull();
		return false;
	}

	public boolean isSuccessfull() {
		return isSuccessfull;
	}
}
