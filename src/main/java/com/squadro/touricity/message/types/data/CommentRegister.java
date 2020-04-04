package com.squadro.touricity.message.types.data;

public class CommentRegister implements ICommentRegister {

	private String username;
	private Comment comment;
	private RouteId routeId;

    public CommentRegister(){}

    public CommentRegister(String username, Comment comment){
    	this.username = username;
    	this.comment = comment;
	}

	public Comment getComment() {
		return comment;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public RouteId getRouteId() {
		return routeId;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public void setRouteId(RouteId routeId) {
		this.routeId = routeId;
	}
}