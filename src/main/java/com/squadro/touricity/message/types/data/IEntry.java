package com.squadro.touricity.message.types.data;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
include = JsonTypeInfo.As.PROPERTY,
property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(
				value = Stop.class,
				name = "stop"
		),
		@JsonSubTypes.Type(
				value = Path.class,
				name = "path"
		)
}
)
public interface IEntry extends IDataType {
	int getDuration();
	int getExpense();
	String getComment();
	int getIndex();

	void setDuration(int duration);
	void setExpense(int expense);
	void setComment(String comment);


}
