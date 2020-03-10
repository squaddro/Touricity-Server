package com.squadro.touricity.session;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

public class SessionCheckQuery extends SelectionQuery {

    private boolean exists = false;
    private SessionCookie session;

    public SessionCheckQuery(SessionCookie session) {
        this.session = session;
    }

    public String getQuery() {
        return "SELECT * FROM " + Database.SESSION +
                " WHERE " + Database.SESSION_SESSION_ID + " LIKE " + Database.value(session.getUuid());
    }

    public boolean onResult(QueryResult result) {
        exists = result.isSuccessfull();

        return false;
    }

    public boolean isExists() {
        return exists;
    }
}
