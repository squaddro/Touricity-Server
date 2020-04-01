package com.squadro.touricity.database.query.commentQueries;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.Comment;
import com.squadro.touricity.message.types.data.CommentRegister;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SelectUsernameFromAccountId extends SelectionQuery {

    private CommentRegister commentRegister;
    private String accountID;


    public boolean isSuccessfull;

    public SelectUsernameFromAccountId(String accountID, CommentRegister commentRegister){
        this.accountID = accountID;
        this.commentRegister = commentRegister;
    }

    @Override
    public String getQuery() {
        return "SELECT * FROM " + Database.USER + " WHERE " + Database.USER_ACCOUNT_ID + " LIKE '" + accountID + "'";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        if(result.isSuccessfull()){
            commentRegister.setUsername(result.getResultSet().getString("username"));
            return true;
        }
        return false;
    }
}
