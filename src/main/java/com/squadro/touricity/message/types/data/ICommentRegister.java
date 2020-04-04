package com.squadro.touricity.message.types.data;

public interface ICommentRegister {
    Comment getComment();
    String getUsername();
    RouteId getRouteId();

    void setComment(Comment comment);
    void setUsername(String username);
    void setRouteId(RouteId routeId);
}
