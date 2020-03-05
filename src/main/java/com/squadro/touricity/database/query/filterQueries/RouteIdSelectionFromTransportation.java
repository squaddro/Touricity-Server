package com.squadro.touricity.database.query.filterQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteIdSelectionFromTransportation extends SelectionQuery {

	private final int path_type;
	private final List<String> list;

	public RouteIdSelectionFromTransportation(int path_type) {
		this.path_type = path_type;
		list = new ArrayList<String>();
	}

	public String getQuery() {
		return "SELECT route_id " +
				" FROM DB_ENTRY " +
				" INNER JOIN DB_ROUTE ON DB_ENTRY.route_id = DB_ROUTE.route_id " +
				" INNER JOIN DB_PATH ON DB_ENTRY.path_id = DB_PATH.path_id " +
				" WHERE DB_PATH.path_type = " + path_type;
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
