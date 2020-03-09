package com.squadro.touricity.database.query.userQueries;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.database.query.InsertionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.session.SessionCookie;

public class CreateSessionQuery extends InsertionQuery {

    private String  sessionCookie;
    private String account_id;

    public CreateSessionQuery(String sessionCookie, String account_id) {
        this.sessionCookie = sessionCookie;
        this.account_id = account_id;
    }

    public String getQuery() {
        return "INSERT INTO " + Database.SESSION + "(account_id,session_id) VALUES('" + account_id + "','" + sessionCookie + "')";
    }

    public boolean onResult(QueryResult result) {
        return false;
    }
}
