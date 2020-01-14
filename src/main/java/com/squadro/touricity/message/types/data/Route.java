package com.squadro.touricity.message.types.data;


public class Route implements IRoute {

	public String creator;
	public String route_id;
	public IEntry[] entries;
	
	public Route(){
		//do nothing
	}
	
	public Route(String creator, String route_id, IEntry[] entries){
		this.creator = creator;
		this.route_id = route_id;
		this.entries = entries;
	}
	
	public String getCreator() {
		return creator;
	}

	public String getRoute_id() {
		return route_id;
	}

	public IEntry[] getEntries() {
		return entries;
	}

	public void setCreator(String creatorId) {
        creator = creatorId;		
	}

	public void setRoute_id(String routeId) {
        route_id = routeId;		
	}

	public void setEntries(IEntry[] setEntries) {
        entries = setEntries;		
	}

}
