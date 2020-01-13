package com.squadro.touricity.database.query.common;

import com.squadro.touricity.database.query.ISingleQuery;
import com.squadro.touricity.database.query.InsertionQuery;
import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.query.pipeline.PipelinedQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.util.Queue;

public class SimpleQueryPipeline extends PipelinedQuery {

	// Shared variables among queries
	private String sharedText;

	protected void PrepareQueue(Queue<ISingleQuery> queue) {

		queue.add(new SelectionQuery() {
			public String getQuery() {
				return "SELECT * FROM TABLES";
			}

			public boolean onResult(QueryResult result) {
				if(result.isSuccessfull())
					sharedText = "successful";
				else
					sharedText = "unsuccessful";
				return true;
			}
		});

		queue.add(new InsertionQuery() {
			public String getQuery() {
				return "INSERT INTO TABLES WHERE 1=0";
			}

			public boolean onResult(QueryResult result) {
				if(result.isSuccessfull())
					System.out.println("Insertion successful selection " + sharedText);
				else
					System.out.println("Insertion unsuccessful selection " + sharedText);
				return true;
			}
		});
	}
}
