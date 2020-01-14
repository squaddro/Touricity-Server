package com.squadro.touricity.message.types.data;

public interface IStop extends IEntry {

	String getLocation_id();
	String getStop_id();

	void setLocation_id(String locationId);
	void setStop_id(String stopId);
}
