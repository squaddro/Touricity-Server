package com.squadro.touricity.session;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;

public class AccountCheckQuery extends SelectionQuery {

    private boolean exists = false;
    private SessionCookie session;

    public AccountCheckQuery(SessionCookie session) {
        this.session = session;
    }

    public String getQuery() {
        return "SELECT * FROM " + Database.ACCOUNT +
                " WHERE " + Database.ACCOUNT_ACCOUNT_ID + " LIKE " + Database.value(session.getUuid());
    }

    public boolean onResult(QueryResult result) {
        exists = result.isSuccessfull();

        return false;
    }

    public boolean isExists() {
        return exists;
    }
}
