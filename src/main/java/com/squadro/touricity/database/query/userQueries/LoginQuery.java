package com.squadro.touricity.database.query.userQueries;

import com.squadro.touricity.database.query.InsertionQuery;
import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class LoginQuery extends SelectionQuery {

    final AtomicReference<String> account_id = new AtomicReference<>();
    private final String username;
    private final String token;
    public boolean isSuccesfull;

    public LoginQuery(String username, String token) {
        this.username = username;
        this.token = token;
    }
    @Override
    public String getQuery() {
        return "SELECT * FROM DB_USER WHERE USERNAME = '" + username + "' AND USER_PASSWORD = '" + token + "'";
    }


    public boolean onResult(QueryResult result) throws SQLException {
        if(result.isSuccessfull()){
            account_id.set(result.getResultSet().getString("account_id"));
            isSuccesfull = true;
        }
        else{
            isSuccesfull = false;}
        return false;
    }
}
