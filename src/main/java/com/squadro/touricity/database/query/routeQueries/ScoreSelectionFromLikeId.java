package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ScoreSelectionFromLikeId extends SelectionQuery {

    private final String like_id;
    private final AtomicReference<Integer> score = new AtomicReference<>();
    private final List<String> list;
    public boolean isSuccessfull;


    public ScoreSelectionFromLikeId(String like_id) {
        this.like_id = like_id;
        list = new ArrayList<>();
    }

    @Override
    public String getQuery() {
        return "SELECT score FROM DB_LIKE WHERE like_id = '" + like_id + "'";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        if(result.isSuccessfull()){
            score.set(result.getResultSet().getInt("score"));
            return true;
        }
        return false;
    }
    public Integer getScore() {
        return score.get();
    }

}
