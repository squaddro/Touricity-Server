package com.squadro.touricity.database.query.filterQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.ResultSet;
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
		return "SELECT route_id, city_id FROM DB_ROUTE WHERE city_id in " +
				"( SELECT city_id FROM DB_CITY WHERE city_name = '" + city + "') " +
				"AND privacy = 2";
	}

	public boolean onResult(QueryResult result) throws SQLException {
		ResultSet rs = result.getResultSet();
		if(result.isSuccessfull()){
			do{
				list.add(rs.getString("route_id"));
			}while(rs.next());
		}
		return false;
	}

	public List<String> getList() {
		return list;
	}
}
