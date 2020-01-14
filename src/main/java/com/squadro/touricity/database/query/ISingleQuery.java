package com.squadro.touricity.database.query;

import com.squadro.touricity.database.ExecutionType;
import com.squadro.touricity.database.result.QueryResult;

public interface ISingleQuery{

	ExecutionType getExecutionType();
	String getQuery();

	boolean onResult(QueryResult result);
	void onError(String e);
}
