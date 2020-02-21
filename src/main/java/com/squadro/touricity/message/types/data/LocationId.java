package com.squadro.touricity.message.types.data;

public class LocationId implements ILocationId{

	private String location_id;

	public LocationId(){ }

	@Override
	public void setLocation_id(String location_id) {
		this.location_id = location_id;
	}

	@Override
	public String getLocation_id() {
		return location_id;
	}
}
