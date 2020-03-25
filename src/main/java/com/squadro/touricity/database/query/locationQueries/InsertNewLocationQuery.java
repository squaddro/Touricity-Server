package com.squadro.touricity.database.query.locationQueries;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.database.query.ISingleQuery;
import com.squadro.touricity.database.query.InsertionQuery;
import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.query.pipeline.PipelinedQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.Location;

import java.sql.SQLException;
import java.util.Queue;

public class InsertNewLocationQuery extends PipelinedQuery {

	private Location location;

	private boolean isSuccessful = false;
	private boolean locationExists = false;

	public InsertNewLocationQuery(Location location) {
		this.location = location;
	}

	public boolean isSuccessful() {
		return isSuccessful;
	}

	public boolean isLocationExists() {
		return locationExists;
	}

	@Override
	protected void PrepareQueue(Queue<ISingleQuery> queue) {
		queue.add(new SelectionQuery() {
			@Override
			public String getQuery() {
				return "SELECT * FROM " + Database.LOCATION + " WHERE " + Database.LOCATION_LOCATION_ID + " LIKE " + Database.value(location.getLocation_id());
			}

			@Override
			public boolean onResult(QueryResult result) throws SQLException {
				locationExists = result.isSuccessfull();
				return !result.isSuccessfull();
			}
		});

		queue.add(new InsertionQuery() {
			@Override
			public String getQuery() {
				return "INSERT INTO " + Database.LOCATION + "(" +
						Database.LOCATION_LOCATION_ID + "," +
						Database.LOCATION_CITY_ID + "," +
						Database.LOCATION_LATITUDE + "," +
						Database.LOCATION_LONGITUDE + ") " +
						"VALUES(" +
						Database.value(location.getLocation_id()) + "," +
						Database.value(location.getCity_id()) + "," +
						location.getLatitude() + "," +
						location.getLongitude() + ")";
			}

			@Override
			public boolean onResult(QueryResult result) throws SQLException {
				isSuccessful = result.isSuccessfull();
				return result.isSuccessfull();
			}
		});
	}
}
