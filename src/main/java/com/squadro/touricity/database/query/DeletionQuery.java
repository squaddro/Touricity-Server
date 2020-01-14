package com.squadro.touricity.database.query;

import com.squadro.touricity.database.ExecutionType;

public abstract class DeletionQuery implements ISingleQuery {
	public ExecutionType getExecutionType() {
		return ExecutionType.DELETE;
	}

	public void onError(String e) {
		System.err.println("Error occurred while deleting database: " + e);
	}
}
