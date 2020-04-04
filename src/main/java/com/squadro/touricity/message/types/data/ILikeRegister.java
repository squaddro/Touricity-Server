package com.squadro.touricity.message.types.data;

public interface ILikeRegister {
    Like getLike();
    String getUsername();
    RouteId getRouteId();

    void setLike(Like like);
    void setUsername(String username);
    void setRouteId(RouteId routeId);
}
