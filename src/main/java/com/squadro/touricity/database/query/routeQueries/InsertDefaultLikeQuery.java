package com.squadro.touricity.database.query.routeQueries;

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

public class InsertDefaultLikeQuery extends PipelinedQuery {

    private String accountID;
    private String routeID;
    public boolean isSuccessfull;


    public InsertDefaultLikeQuery(String accountID, String routeID){
        this.accountID = accountID;
        this.routeID = routeID;
    }
    @Override
    protected void PrepareQueue(Queue<ISingleQuery> queue) {

        String likeId = String.valueOf(UUID.randomUUID());
        queue.add(new InsertionQuery() {
            @Override
            public String getQuery() {
                return "INSERT INTO " + Database.LIKE + "(account_id,like_id,score) VALUES('" + accountID + "','" + likeId + "','" + 0 + "')";
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
                return "INSERT INTO " + Database.ROUTE_LIKE + "(route_id,like_id) VALUES('" + routeID + "','" + likeId + "')";
            }

            @Override
            public boolean onResult(QueryResult result) throws SQLException {
                isSuccessfull = result.isSuccessfull();
                return true;
            }
        });
    }
}
