package com.squadro.touricity.database.query.likeQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.Like;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class GetLikeInfoQuery extends SelectionQuery {

	final AtomicReference<String> account_id = new AtomicReference<>();
	final AtomicReference<Integer> score = new AtomicReference<>();
	private final String like_id;


	public GetLikeInfoQuery(String like_id) {
		this.like_id = like_id;
	}

	@Override
	public String getQuery() {
		return "SELECT * FROM DB_LIKE WHERE LIKE_ID = '" + like_id + "'";
	}

	@Override
	public boolean onResult(QueryResult result) throws SQLException {
		if(result.isSuccessfull()){
			account_id.set(result.getResultSet().getString("account_id"));
			score.set(result.getResultSet().getInt("score"));}
		return false;
	}

	public Like getLike(){
		Like like = new Like(like_id, account_id.get(), score.get());
		return like;
	}
}
