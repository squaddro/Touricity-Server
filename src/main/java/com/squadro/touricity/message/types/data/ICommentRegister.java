package com.squadro.touricity.message.types.data;

public interface ICommentRegister {
    String getCommentId();
    String getCommentDesc();
    String getUsername();
    String getRouteId();

    void setCommentId(String commentId);
    void setCommentDesc(String commentDesc);
    void setUsername(String username);
    void setRouteId(String routeId);
}
