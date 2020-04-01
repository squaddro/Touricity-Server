package com.squadro.touricity.message.types.data;

public class Comment implements IComment {
    private String commentId;
    private String commentDesc;

    public Comment() {}

    public Comment(String commentId, String commentDesc) {
        this.commentId = commentId;
        this.commentDesc = commentDesc;
    }

    @Override
    public String getCommentId() {
        return commentId;
    }

    @Override
    public String getCommentDesc() {
        return commentDesc;
    }

    @Override
    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    @Override
    public void setCommentDesc(String commentDesc) {
        this.commentDesc = commentDesc;
    }
}
