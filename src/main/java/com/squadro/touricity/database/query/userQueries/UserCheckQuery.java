package com.squadro.touricity.database.query.userQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.Like;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class UserCheckQuery extends SelectionQuery {

    final AtomicReference<String> account_id = new AtomicReference<>();
    final AtomicReference<String> user_password = new AtomicReference<>();
    private final String username;
    private boolean doesUserExist;


    public UserCheckQuery(String username) {
        this.username = username;
    }

    @Override
    public String getQuery() {
        return "SELECT * FROM DB_USER WHERE USERNAME = '" + username + "'";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        if(result.isSuccessfull()){
            account_id.set(result.getResultSet().getString("account_id"));
            user_password.set(result.getResultSet().getString("user_password"));
            doesUserExist = true;
        }
        else{
        doesUserExist = false;}
        return false;
    }

    public boolean getDoesUserExists(){
        return doesUserExist;
    }
    public String getUserPassword(){
        return String.valueOf(user_password);
    }
    public String getAccountId(){
        return String.valueOf(account_id);
    }
}
