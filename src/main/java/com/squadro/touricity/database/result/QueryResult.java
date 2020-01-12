package com.squadro.touricity.database.result;

import com.squadro.touricity.database.ExecutionType;

import java.sql.ResultSet;

public class QueryResult {
	private boolean successfull;
	private ResultSet resultSet;

	public QueryResult(boolean successfull) {
		this(successfull, null);
	}

	public QueryResult(boolean successfull, ResultSet resultSet) {
		this.successfull = successfull;
		this.resultSet = resultSet;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public boolean isSuccessfull() {
		return successfull;
	}

}
