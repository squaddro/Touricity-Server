package com.squadro.touricity.database.query.userQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class GetTokenFromAccountId extends SelectionQuery {

    private final String account_id;
    final AtomicReference<String> token = new AtomicReference<>();
    private boolean doesUserExist;


    public GetTokenFromAccountId(String account_id) {
        this.account_id = account_id;
    }

    @Override
    public String getQuery() {
        return "SELECT * FROM DB_USER WHERE USERNAME = '" + account_id + "'";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        if(result.isSuccessfull()){
            token.set(result.getResultSet().getString("user_password"));
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
        return String.valueOf(token);
    }
    public String getAccountId(){
        return String.valueOf(account_id);
    }
}
