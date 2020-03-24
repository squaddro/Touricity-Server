package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.query.locationQueries.GetLocationInfoQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.Location;
import com.squadro.touricity.message.types.data.Stop;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class StopListSelectionFromRouteId extends SelectionQuery {

	private final String route_id;
	private final List<Stop> list;

	public StopListSelectionFromRouteId(String route_id) {
		this.route_id = route_id;
		this.list = new ArrayList<>();
	}

	@Override
	public String getQuery() {
		return "SELECT DB_ENTRY.stop_id,expense,duration,comment_desc,pointer,location_id FROM db_entry " +
				"INNER JOIN DB_STOP ON DB_ENTRY.stop_id = DB_STOP.stop_id "+
				"WHERE route_id = '" + route_id + "' AND DB_ENTRY.stop_id IS NOT NULL ORDER BY pointer ASC";
	}

	@Override
	public boolean onResult(QueryResult result) throws SQLException {
		ResultSet rs = result.getResultSet();

		if (result.isSuccessfull()) {
			do {

				Stop stop = new Stop();

				stop.setStop_id(rs.getString("stop_id"));
				stop.setExpense(rs.getInt("expense"));
				stop.setDuration(rs.getInt("duration"));
				stop.setComment(rs.getString("comment_desc"));
				stop.setIndex(rs.getInt("pointer"));
				stop.setLocation((Location)rs.getObject("location"));

				AtomicReference<Location> location = new AtomicReference<Location>();
				GetLocationInfoQuery locationInfoQuery = new GetLocationInfoQuery(stop.getLocation().getLocation_id());
				locationInfoQuery.execute();
				stop.setLocation(locationInfoQuery.getLocation());

				list.add(stop);
			} while (rs.next());
		}
		return false;
	}

	public List<Stop> getList() {
		return list;
	}
}
