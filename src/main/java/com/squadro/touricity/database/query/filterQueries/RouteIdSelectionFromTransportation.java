package com.squadro.touricity.database.query.filterQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteIdSelectionFromTransportation extends SelectionQuery {

	private final List<Integer> path_type;
	private final List<String> list;

	public RouteIdSelectionFromTransportation(List<Integer> path_type) {
		this.path_type = path_type;
		list = new ArrayList<String>();
	}

	public String getQuery() {
		int size = path_type.size();
		int count = 0;
		String base =  "SELECT DB_ROUTE.route_id " +
				" FROM DB_ENTRY " +
				" INNER JOIN DB_ROUTE ON DB_ENTRY.route_id = DB_ROUTE.route_id " +
				" INNER JOIN DB_PATH ON DB_ENTRY.path_id = DB_PATH.path_id ";
		while(size > 1){
			String innerQuery = "WHERE DB_PATH.path_type = " + path_type.get(count) + "OR" ;
			base = base.concat(innerQuery);
			count++;
			size--;
		}
		base = base.concat("WHERE DB_PATH.path_type = " + path_type.get(0));
		return base;
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
