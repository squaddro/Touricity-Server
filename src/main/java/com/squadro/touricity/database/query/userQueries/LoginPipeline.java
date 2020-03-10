package com.squadro.touricity.database.query.userQueries;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.database.query.ISingleQuery;
import com.squadro.touricity.database.query.InsertionQuery;
import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.query.pipeline.PipelinedQuery;
import com.squadro.touricity.database.query.routeQueries.InsertNewEntryQuery;
import com.squadro.touricity.database.result.QueryResult;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.Queue;

public class LoginPipeline extends PipelinedQuery {

    public boolean isSuccessfull;

    private String sessionId;
    private String username;
    private String password;

    private String accountID;

    public LoginPipeline(String sessionId, String username, String password) {
        this.sessionId = sessionId;
        this.username = username;
        this.password = password;
    }

    @Override
    protected void PrepareQueue(Queue<ISingleQuery> queue) {
        queue.add(new SelectionQuery() {
            @Override
            public String getQuery() {
                return "SELECT * FROM " + Database.USER + " WHERE " + Database.USER_USERNAME + " LIKE '" + username + "' " +
                        " AND " + Database.USER_PASSWORD + " LIKE '" + password + "'";
            }

            @Override
            public boolean onResult(QueryResult result) throws SQLException {
                if(result.isSuccessfull()) {
                    accountID = result.getResultSet().getString(Database.USER_ACCOUNT_ID);

                    return true;
                }

                return false;
            }
        });

        queue.add(new SessionDeletionQuery(sessionId));

        queue.add(new InsertionQuery() {
            @Override
            public String getQuery() {
                return "INSERT INTO " + Database.SESSION + "(account_id,session_id) VALUES('" + accountID + "','" + sessionId + "')";
            }

            @Override
            public boolean onResult(QueryResult result) throws SQLException {

                isSuccessfull = result.isSuccessfull();
                return true;
            }
        });
    }
}
