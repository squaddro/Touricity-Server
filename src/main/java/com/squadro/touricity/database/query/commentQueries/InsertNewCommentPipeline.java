package com.squadro.touricity.database.query.commentQueries;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.database.query.ISingleQuery;
import com.squadro.touricity.database.query.InsertionQuery;
import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.query.pipeline.PipelinedQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.CommentRegister;

import java.sql.SQLException;
import java.util.Queue;
import java.util.UUID;

public class InsertNewCommentPipeline extends PipelinedQuery {

    private String accountID;
    private String username;
    private CommentRegister commentRegister;
    public boolean isSuccessfull;


    public InsertNewCommentPipeline(CommentRegister commentRegister){
        this.commentRegister = commentRegister;
    }
    @Override
    protected void PrepareQueue(Queue<ISingleQuery> queue) {

        queue.add(new SelectionQuery() {
            @Override
            public String getQuery() {
                return "SELECT * FROM " + Database.USER + " WHERE " + Database.USER_USERNAME + " LIKE '" + commentRegister.getUsername() + "'";
            }

            @Override
            public boolean onResult(QueryResult result) throws SQLException {
                if(result.isSuccessfull()) {
                    accountID = result.getResultSet().getString(Database.USER_ACCOUNT_ID);
                    System.out.println("1.checkpoint");
                    System.out.println(accountID);
                    String commentId = String.valueOf(UUID.randomUUID());
                    commentRegister.setCommentId(commentId);
                    System.out.println(commentRegister.getCommentId());
                    System.out.println(commentRegister.getCommentDesc());
                    return true;
                }
                System.out.println("2.checkpoint");
                return false;
            }
        });

        queue.add(new InsertionQuery() {
            @Override
            public String getQuery() {
                return "INSERT INTO " + Database.COMMENT + "(account_id,comment_id,comment_desc) VALUES('" + accountID + "','" + commentRegister.getCommentId() + "','" + commentRegister.getCommentDesc() + "')";
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
                return "INSERT INTO " + Database.ROUTE_COMMENT + "(route_id,comment_id) VALUES('" + commentRegister.getRouteId() + "','" + commentRegister.getCommentId() + "')";
            }

            @Override
            public boolean onResult(QueryResult result) throws SQLException {
                isSuccessfull = result.isSuccessfull();
                return true;
            }
        });

    }
}
