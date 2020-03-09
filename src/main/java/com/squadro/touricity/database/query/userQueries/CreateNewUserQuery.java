package com.squadro.touricity.database.query.userQueries;

import com.squadro.touricity.database.query.InsertionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class CreateNewUserQuery extends InsertionQuery {

    private final String username;
    private final String user_password;
    private final String account_id;

    public CreateNewUserQuery(String account_id, String username, String user_password) {
        this.account_id = account_id;
        this.username = username;
        this.user_password = user_password;
    }
    @Override
    public String getQuery() {
        String query = "INSERT INTO db_user(account_id,username,user_password) VALUES('" + account_id + "','" + username + "','" + user_password + "')";
        return query;
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        return false;
    }
}
