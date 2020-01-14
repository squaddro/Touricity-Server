package com.squadro.touricity.message.types.data;


public class Location implements ILocation {

	private String location_id;
	private String latitude;
	private String longitude;
	
	public Location(){
	}
	
	public Location(String locationId, String latitude, String longitude){
		this.location_id = locationId;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public String getLocation_id() {
		return location_id;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLocation_id(String locationId) {
        location_id = locationId;		
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}
