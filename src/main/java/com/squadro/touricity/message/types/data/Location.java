package com.squadro.touricity.message.types.data;

public class Location implements ILocation {

	private String location_id;
	private String cityId;
	private double latitude;
	private double longitude;

	public Location(){
	}
	
	public Location(String locationId, double latitude, double longitude){
		this.location_id = locationId;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Location(String locationId, String cityId, double latitude, double longitude){
		this.location_id = locationId;
		this.cityId = cityId;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public String getLocation_id() {
		return location_id;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getCity_id() {
		return cityId;
	}

	public void setLocation_id(String locationId) {
        location_id = locationId;		
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setCity_id(String cityId) {
		this.cityId = cityId;
	}

}
