package com.squadro.touricity.database.query.common;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

public class SimpleSelectionQuery extends SelectionQuery {

	public String getQuery() {
		return "SELECT * FROM ACCOUNT";
	}

	public boolean onResult(QueryResult result) {
		result.getResultSet();
		return false;
	}
}
