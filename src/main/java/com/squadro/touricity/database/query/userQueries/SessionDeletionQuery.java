package com.squadro.touricity.database.query.userQueries;

import com.squadro.touricity.database.query.DeletionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.RouteId;

import java.sql.SQLException;

public class SessionDeletionQuery extends DeletionQuery {

    private final String session_id;

    private boolean isSuccessfull;

    public SessionDeletionQuery(String session_id) {
        this.session_id = session_id;
        isSuccessfull = false;
    }

    @Override
    public String getQuery() {
        return "DELETE FROM DB_SESSION WHERE SESSION_ID = '" + session_id + "'";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        isSuccessfull = result.isSuccessfull();
        return false;
    }
    public boolean isSuccessfull() {
        return isSuccessfull;
    }

}
