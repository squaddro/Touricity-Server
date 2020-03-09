package com.squadro.touricity.database.query.userQueries;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.session.SessionCookie;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class SessionCheckQuery extends SelectionQuery {

    private boolean exists = false;
    private final AtomicReference<String> account_id = new AtomicReference<>();
    private String session;

    public SessionCheckQuery(String session) {
        this.session = session;
    }

    public String getQuery() {
        return "SELECT * FROM " + Database.SESSION +
                " WHERE " + Database.SESSION_SESSION_ID + " LIKE " + Database.value(session);
    }

    public boolean onResult(QueryResult result) throws SQLException {
        exists = result.isSuccessfull();
        if(result.isSuccessfull()){
            account_id.set(result.getResultSet().getString("account_id"));
        }
        return false;
    }

    public boolean isExists() {
        return exists;
    }

    public String getAccountId(){
        return account_id.get();
    }

}
