package com.squadro.touricity.database.query.commentQueries;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.database.query.ISingleQuery;
import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.query.pipeline.PipelinedQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.Comment;
import com.squadro.touricity.message.types.data.CommentRegister;
import com.squadro.touricity.message.types.data.RouteId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class SelectCommentIDFromRouteID extends SelectionQuery {
    private String routeId;
    private final List<String> list;
    private final List<CommentRegister> commentList;

    public boolean isSuccessfull;

    public SelectCommentIDFromRouteID(String routeId){
        this.routeId = routeId;
        list = new ArrayList<String>();
        commentList = new ArrayList<CommentRegister>();
    }

    @Override
    public String getQuery() {
        return "SELECT * FROM " + Database.ROUTE_COMMENT + " WHERE " + Database.ROUTE_COMMENT_ROUTE_ID + " LIKE '" + routeId + "'";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        if(result.isSuccessfull()){
            do{
                list.add(result.getResultSet().getString("comment_id"));
            }while(result.getResultSet().next());
            isSuccessfull = true;
            return true;
        }
        return false;
    }

    public List<String> getList(){
        return list;
    }
}
