package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.Route;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class RouteInstancesSelectionFromRouteId extends SelectionQuery {
	private final AtomicReference<Route> route = new AtomicReference<>();
	private final String route_id;

	public RouteInstancesSelectionFromRouteId(String route_id) {
		this.route_id = route_id;
	}

	@Override
	public String getQuery() {
		return "SELECT creator,title,city_id,privacy FROM db_route WHERE route_id = '" + route_id + "'";
	}

	@Override
	public boolean onResult(QueryResult result) throws SQLException {
		Route resultRoute = new Route();
		ResultSet resultSet = result.getResultSet();
		if(result.isSuccessfull()){
			resultRoute.setCity_id("111111111111111111111111111111111111");
			resultRoute.setTitle(resultSet.getString("title"));
			resultRoute.setCreator(resultSet.getString("creator"));
			resultRoute.setPrivacy(resultSet.getInt("privacy"));
			route.set(resultRoute);
		}
		return false;
	}
	public Route getRoute(){
		return route.get();
	}
}
