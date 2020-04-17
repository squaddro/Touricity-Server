package com.squadro.touricity.database.query.filterQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.ResultSet;
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
		return "SELECT DB_ROUTE.route_id,SUM(expense), SUM(duration) " +
				"FROM DB_ROUTE INNER JOIN DB_ENTRY ON DB_ROUTE.route_id = DB_ENTRY.route_id " +
				"GROUP BY DB_ROUTE.route_id " +
				"HAVING AVG(expense) <= '" + averageCost + "' AND SUM(duration) <= '" + duration + "'";
	}

	public boolean onResult(QueryResult result) throws SQLException {
		ResultSet resultSet = result.getResultSet();
		if(result.isSuccessfull()){
			do{
				list.add(resultSet.getString("route_id"));
			}while(resultSet.next());
		}
		return false;
	}

	public List<String> getList() {
		return list;
	}
}
