package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CreatorSelectionFromRouteId extends SelectionQuery {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
	private final AtomicReference<String> creator = new AtomicReference<>();
	private final String route_id;

	public CreatorSelectionFromRouteId(String route_id) {
		this.route_id = route_id;
	}

	@Override
	public String getQuery() {
		return "SELECT creator FROM db_route WHERE route_id = '" + route_id + "'";
	}

	@Override
	public boolean onResult(QueryResult result) throws SQLException {
		if (result.getResultSet().next()) {
		    logger.info("creator is " + result.getResultSet().getString("creator"));
			creator.set(result.getResultSet().getString("creator"));
		}else{
		    logger.info("creator result set is empty");
        }
		return false;
	}

	public String getCreator() {
		return creator.get();
	}
}
