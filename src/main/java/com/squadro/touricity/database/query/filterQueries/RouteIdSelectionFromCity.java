package com.squadro.touricity.database.query.filterQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteIdSelectionFromCity extends SelectionQuery {

	private final String city;
	private final List<String> list;

	public RouteIdSelectionFromCity(String city) {
		this.city = city;
		list = new ArrayList<String>();
	}

	public String getQuery() {
		String cityQuery = "SELECT city_id FROM DB_CITY WHERE " + "city_name" + "=" + city.toUpperCase();
		return "SELECT route_id FROM DB_ROUTE WHERE " + "(" + cityQuery + ") AND privacy = 0";
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
