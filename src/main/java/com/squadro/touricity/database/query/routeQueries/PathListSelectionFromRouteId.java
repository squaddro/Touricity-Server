package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.Path;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PathListSelectionFromRouteId extends SelectionQuery {

	private final String route_id;
	private final List<Path> list;

	public PathListSelectionFromRouteId(String route_id) {
		this.route_id = route_id;
		this.list = new ArrayList<>();
	}

	@Override
	public String getQuery() { //TODO: select Entry_id
		return "SELECT path_id,expense,duration,comment_desc,pointer FROM db_entry WHERE route_id = '" + route_id + "' AND path_id IS NOT NULL ORDER BY pointer ASC";
	}

	@Override
	public boolean onResult(QueryResult result) throws SQLException {
		ResultSet rs = result.getResultSet();
		Path path;

		if (result.isSuccessfull()) {
			do {
				path = new Path();
				path.setPath_id(rs.getString("path_id"));
				path.setExpense(rs.getInt("expense"));
				path.setDuration(rs.getInt("duration"));
				path.setComment(rs.getString("comment_desc"));
				path.setIndex(rs.getInt("pointer"));

				list.add(path);
			} while (rs.next());
		}
		return false;
	}

	public List<Path> getList() {
		return list;
	}
}
