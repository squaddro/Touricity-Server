package com.squadro.touricity.message.types.data;

import java.util.List;

public class CommentRegisterList {

	private List<CommentRegister> commentRegisterList;

    public CommentRegisterList(List<CommentRegister> commentRegisterList){
    	this.commentRegisterList = commentRegisterList;
	}

	public List<CommentRegister> getCommentRegisterList(){
    	return commentRegisterList;
	}
}