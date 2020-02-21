package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.Stop;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StopListSelectionFromRouteId extends SelectionQuery {

	private final String route_id;
	private final List<Stop> list;

	public StopListSelectionFromRouteId(String route_id) {
		this.route_id = route_id;
		this.list = new ArrayList<>();
	}

	@Override
	public String getQuery() { //TODO: select Entry_id
		return "SELECT stop_id,expense,duration,comment_desc,pointer FROM db_entry WHERE route_id = '" + route_id + "' AND stop_id IS NOT NULL ORDER BY pointer ASC";
	}

	@Override
	public boolean onResult(QueryResult result) throws SQLException {
		ResultSet rs = result.getResultSet();
		Stop stop;

		if (result.isSuccessfull()) {
			do {
				stop = new Stop();
				stop.setStop_id(rs.getString("stop_id"));
				stop.setExpense(rs.getInt("expense"));
				stop.setDuration(rs.getInt("duration"));
				stop.setComment(rs.getString("comment_desc"));
				stop.setIndex(rs.getInt("pointer"));

				list.add(stop);
			} while (rs.next());
		}
		return false;
	}

	public List<Stop> getList() {
		return list;
	}
}
