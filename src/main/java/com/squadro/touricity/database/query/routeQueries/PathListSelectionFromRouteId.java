package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.ByteArrays;
import com.squadro.touricity.database.Database;
import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.IPath;
import com.squadro.touricity.message.types.data.Path;
import com.squadro.touricity.message.types.data.enumeration.PathType;

import javax.xml.crypto.Data;
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
		return "SELECT DB_ENTRY.path_id,expense,duration,comment_desc,pointer,vertices,path_type FROM db_entry " +
				"INNER JOIN DB_PATH ON DB_ENTRY.path_id = DB_PATH.path_id "+
				"WHERE route_id = '" + route_id + "' AND DB_ENTRY.path_id IS NOT NULL ORDER BY pointer ASC";
	}

	@Override
	public boolean onResult(QueryResult result) throws SQLException {
		ResultSet rs = result.getResultSet();
		Path path;

		if (result.isSuccessfull()) {
			do {
				path = new Path();
				path.setPath_id(rs.getString(Database.PATH_PATH_ID));
				path.setExpense(rs.getInt(Database.ENTRY_EXPENSE));
				path.setDuration(rs.getInt(Database.ENTRY_DURATION));
				path.setComment(rs.getString(Database.ENTRY_COMMENT_DESCRIPTION));
				path.setIndex(rs.getInt(Database.ENTRY_POINTER));
				path.setVertices(ByteArrays.Decoders.decodePathVertexArray(rs.getString(Database.PATH_VERTICES)));
				path.setPath_type(PathType.values()[rs.getInt(Database.PATH_PATH_TYPE)]);

				list.add(path);
			} while (rs.next());
		}
		return false;
	}

	public List<Path> getList() {
		return list;
	}
}
