package com.squadro.touricity.database.query.commentQueries;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.Comment;
import com.squadro.touricity.message.types.data.CommentRegister;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectCommentFromCommentID extends SelectionQuery {
    private String commentId;
    private CommentRegister commentRegister;
    private final List<CommentRegister> commentList;

    public boolean isSuccessfull;

    public SelectCommentFromCommentID(String commentId){
        this.commentId = commentId;
        commentList = new ArrayList<CommentRegister>();
    }

    @Override
    public String getQuery() {
        return "SELECT * FROM " + Database.COMMENT + " WHERE " + Database.COMMENT_COMMENT_ID + " LIKE '" + commentId + "'";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        if(result.isSuccessfull()){
            Comment comment = new Comment();
            comment.setCommentDesc(result.getResultSet().getString("comment_desc"));
            comment.setCommentId(result.getResultSet().getString("comment_id"));
            commentRegister = new CommentRegister();
            commentRegister.setComment(comment);
            commentRegister.setUsername(result.getResultSet().getString("account_id"));
            return true;
        }
        return false;
    }

    public CommentRegister getCommentRegister(){
        return commentRegister;
    }
}
