package com.squadro.touricity.message.types.data;

public interface ILocation extends IDataType {
	String getLocation_id();
	double getLatitude();
	double getLongitude();
	String getCity_id();

	void setLocation_id(String locationId);
	void setLatitude(double latitude);
	void setLongitude(double longitude);
	void setCity_id(String cityId);
}
