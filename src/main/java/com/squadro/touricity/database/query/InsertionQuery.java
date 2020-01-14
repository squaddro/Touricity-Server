package com.squadro.touricity.database.query;

import com.squadro.touricity.database.ExecutionType;

public abstract class InsertionQuery implements ISingleQuery {
	public ExecutionType getExecutionType() {
		return ExecutionType.INSERT;
	}

	public void onError(String e) {
		System.err.println("Error occurred while inserting to database: " + e);
	}
}
