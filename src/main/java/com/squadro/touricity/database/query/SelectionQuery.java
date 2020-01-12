package com.squadro.touricity.database.query;

import com.squadro.touricity.database.ExecutionType;

public abstract class SelectionQuery implements ISingleQuery {
	public ExecutionType getExecutionType() {
		return ExecutionType.SELECT;
	}

	public void onError(String e) {
		System.err.println("Error occurred while selecting from database: " + e);
	}
}
