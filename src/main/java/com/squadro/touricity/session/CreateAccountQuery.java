package com.squadro.touricity.session;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.database.query.InsertionQuery;
import com.squadro.touricity.database.result.QueryResult;
import org.slf4j.LoggerFactory;

public class CreateAccountQuery extends InsertionQuery {

    private SessionCookie sessionCookie;

    public CreateAccountQuery(SessionCookie sessionCookie) {
        this.sessionCookie = sessionCookie;
    }

    public String getQuery() {
        return "INSERT INTO " + Database.ACCOUNT +
                " VALUES (" + Database.value(sessionCookie.getUuid()) + ")";
    }

    public boolean onResult(QueryResult result) {
        return false;
    }
}
