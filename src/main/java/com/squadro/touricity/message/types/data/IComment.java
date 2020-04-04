package com.squadro.touricity.message.types.data;

public interface IComment extends IDataType {

	String getCommentId();
	String getCommentDesc();

	void setCommentId(String commentId);

	void setCommentDesc(String commentDesc);
}
