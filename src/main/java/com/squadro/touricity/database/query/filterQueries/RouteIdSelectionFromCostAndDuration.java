package com.squadro.touricity.database.query.filterQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteIdSelectionFromCostAndDuration extends SelectionQuery {

	private final int averageCost;
	private final int duration;
	private final List<String> list;

	public RouteIdSelectionFromCostAndDuration(int averageCost, int duration) {
		this.averageCost = averageCost;
		this.duration = duration;
		list = new ArrayList<String>();
	}

	public String getQuery() {
		return "SELECT route_id,SUM(expense) AS totalExpense, SUM(duration) AS totalDuration " +
				"FROM DB_ROUTE INNER JOIN DB_ENTRY ON DB_ROUTE.route_id = DB_ENTRY.route_id" +
				"HAVING totalExpense <= " + averageCost + "AND totalDuration <= " + duration;
	}

	public boolean onResult(QueryResult result) throws SQLException {
		while (result.getResultSet().next()) {
			list.add(result.getResultSet().getString("route_id"));
		}
		return false;
	}

	public List<String> getList() {
		return list;
	}
}
