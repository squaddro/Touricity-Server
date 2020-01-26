package com.squadro.touricity.message.types.data;

public interface ILocation {
	String getLocation_id();
	double getLatitude();
	double getLongitude();

	void setLocation_id(String locationId);
	void setLatitude(double latitude);
	void setLongitude(double longitude);
}
