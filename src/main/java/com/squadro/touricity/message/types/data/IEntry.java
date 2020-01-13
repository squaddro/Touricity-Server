package com.squadro.touricity.message.types.data;

public interface IEntry extends IDataType {
	int getDuration();
	int getExpense();
	String getComment();

	void setDuration(int duration);
	void setExpense(int expense);
	void setComment(String comment);
}
