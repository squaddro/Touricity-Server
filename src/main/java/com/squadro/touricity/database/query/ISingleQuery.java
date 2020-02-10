package com.squadro.touricity.database.query;

import com.squadro.touricity.database.ExecutionType;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.SQLException;

public interface ISingleQuery{

	void execute();

	ExecutionType getExecutionType();
	String getQuery();

	boolean onResult(QueryResult result) throws SQLException;
	void onError(String e);
}
