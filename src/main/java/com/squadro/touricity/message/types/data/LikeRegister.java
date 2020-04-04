package com.squadro.touricity.message.types.data;

public class LikeRegister implements ILikeRegister {

	private String username;
	private Like like;
	private RouteId routeId;

    public LikeRegister(){}

    public LikeRegister(String username, Like like){
    	this.username = username;
    	this.like = like;
	}

	public Like getLike() {
		return like;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public RouteId getRouteId() {
		return routeId;
	}

	public void setLike(Like like) {
		this.like = like;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public void setRouteId(RouteId routeId) {
		this.routeId = routeId;
	}
}