package com.squadro.touricity.message.types.data;


public class Stop extends Entry implements IStop{

	public String location_id;
	public String stop_id;
	
	public Stop(){
		
	}
	
	public Stop(String location_id, String stop_id){
		this.location_id = location_id;
		this.stop_id = stop_id;
	}
	
	public String getLocation_id() {
		return location_id;
	}

	public String getStop_id() {
		return stop_id;
	}

	public void setLocation_id(String locationId) {
		location_id = locationId;
	}

	public void setStop_id(String stopId) {
		stop_id = stopId;
	}
	
}