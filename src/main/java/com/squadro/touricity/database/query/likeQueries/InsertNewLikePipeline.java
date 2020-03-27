package com.squadro.touricity.database.query.likeQueries;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.database.query.ISingleQuery;
import com.squadro.touricity.database.query.InsertionQuery;
import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.query.pipeline.PipelinedQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.CommentRegister;
import com.squadro.touricity.message.types.data.LikeRegister;

import java.sql.SQLException;
import java.util.Queue;
import java.util.UUID;

public class InsertNewLikePipeline extends PipelinedQuery {

    private String accountID;
    private String username;
    private LikeRegister likeRegister;
    public boolean isSuccessfull;


    public InsertNewLikePipeline(LikeRegister likeRegister){
        this.likeRegister = likeRegister;
    }
    @Override
    protected void PrepareQueue(Queue<ISingleQuery> queue) {

        queue.add(new SelectionQuery() {
            @Override
            public String getQuery() {
                return "SELECT * FROM " + Database.USER + " WHERE " + Database.USER_USERNAME + " LIKE '" + likeRegister.getUsername() + "'";
            }

            @Override
            public boolean onResult(QueryResult result) throws SQLException {
                if(result.isSuccessfull()) {
                    accountID = result.getResultSet().getString(Database.USER_ACCOUNT_ID);
                    String likeId = String.valueOf(UUID.randomUUID());
                    likeRegister.getLike().setLike_id(likeId);
                    return true;
                }
                return false;
            }
        });

        queue.add(new InsertionQuery() {
            @Override
            public String getQuery() {
                return "INSERT INTO " + Database.LIKE + "(account_id,like_id,score) VALUES('" + accountID + "','" + likeRegister.getLike().getLike_id() + "','" + likeRegister.getLike().getScore() + "')";
            }

            @Override
            public boolean onResult(QueryResult result) throws SQLException {
                if(result.isSuccessfull()) {
                    return true;
                }
                return false;
            }
        });

        queue.add(new InsertionQuery() {
            @Override
            public String getQuery() {
                return "INSERT INTO " + Database.ROUTE_LIKE + "(route_id,like_id) VALUES('" + likeRegister.getRouteId().getRoute_id() + "','" + likeRegister.getLike().getLike_id() + "')";
            }

            @Override
            public boolean onResult(QueryResult result) throws SQLException {
                isSuccessfull = result.isSuccessfull();
                return true;
            }
        });

    }
}
