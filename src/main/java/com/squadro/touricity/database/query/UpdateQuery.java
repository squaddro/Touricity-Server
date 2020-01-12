package com.squadro.touricity.database.query;

import com.squadro.touricity.database.ExecutionType;

public abstract class UpdateQuery implements ISingleQuery {
	public ExecutionType getExecutionType() {
		return ExecutionType.UPDATE;
	}

	public void onError(String e) {
		System.err.println("Error occurred while updating on database: " + e);
	}
}
