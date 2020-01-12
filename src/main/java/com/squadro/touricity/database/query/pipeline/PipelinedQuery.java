package com.squadro.touricity.database.query.pipeline;

import com.squadro.touricity.database.query.ISingleQuery;

import java.util.LinkedList;
import java.util.Queue;

public abstract class PipelinedQuery implements IPipelinedQuery {
	public Queue<ISingleQuery> getQueries() {
		Queue<ISingleQuery> queue = new LinkedList<ISingleQuery>();
		PrepareQueue(queue);
		return queue;
	}

	protected abstract void PrepareQueue(Queue<ISingleQuery> queue);
}
