package com.squadro.touricity.message.types.data;

public class CommentRegister implements ICommentRegister {

	private String username;
	private String commentId;
	private String commentDesc;
	private String routeId;

    public CommentRegister(){}

    public CommentRegister(String username, String commentId, String commentDesc){
    	this.username = username;
    	this.commentId = commentId;
    	this.commentDesc = commentDesc;
	}

	public String getCommentId() {
		return commentId;
	}

	@Override
	public String getCommentDesc() {
		return commentDesc;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public String getRouteId() {
		return routeId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public void setCommentDesc(String commentDesc) {
		this.commentDesc = commentDesc;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
}