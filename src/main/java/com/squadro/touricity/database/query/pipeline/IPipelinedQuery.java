package com.squadro.touricity.database.query.pipeline;

import com.squadro.touricity.database.query.ISingleQuery;

import java.util.Queue;

public interface IPipelinedQuery {
    Queue<ISingleQuery> getQueries();
}
