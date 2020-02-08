package com.squadro.touricity.message.types.data;

public interface IRoute extends IDataType{

	String getCreator();
	String getRoute_id();
	IEntry[] getEntries();
	String getCity_id();
	String getTitle();
	int getPrivacy();

	void setCreator(String creatorId);
	void setRoute_id(String routeId);
	void setEntries(IEntry[] setEntries);
	void setCity_id(String cityId);
	void setTitle(String title);
	void setPrivacy(int privacy);
}
