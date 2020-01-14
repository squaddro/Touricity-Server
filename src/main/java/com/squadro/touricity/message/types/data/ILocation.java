package com.squadro.touricity.message.types.data;

public interface ILocation {
	String getLocation_id();
	String getLatitude();
	String getLongitude();

	void setLocation_id(String locationId);
	void setLatitude(String latitude);
	void setLongitude(String longitude);
}
