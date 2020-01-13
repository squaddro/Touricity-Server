package com.squadro.touricity.message.types.data;

public interface IRoute extends IDataType{

	String getCreator();
	String getRoute_id();
	IEntry[] getEntries();

	void setCreator(String creatorId);
	void setRoute_id(String routeId);
	void setEntries(IEntry[] setEntries);
}
