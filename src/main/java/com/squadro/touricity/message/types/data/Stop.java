package com.squadro.touricity.message.types.data;

import com.squadro.touricity.message.types.IMessage;

public class Stop extends Entry implements IStop {

	private String location_id;
	private String stop_id;

	private Location location;
	
	public Stop(){
		
	}
	
	public Stop(String location_id, String stop_id, int expense, int duration, String comment){
		this.location_id = location_id;
		this.stop_id = stop_id;
		this.expense = expense;
		this.duration = duration;
		this.comment = comment;
	}

	public Stop(String location_id, String stop_id, int expense, int duration, String comment, int index){
		this.location_id = location_id;
		this.stop_id = stop_id;
		this.expense = expense;
		this.duration = duration;
		this.comment = comment;
		this.index = index;
	}
	
	public String getLocation_id() {
		return location_id;
	}

	public String getStop_id() {
		return stop_id;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setLocation_id(String locationId) {
		location_id = locationId;
	}

	public void setStop_id(String stopId) {
		stop_id = stopId;
	}
	
}