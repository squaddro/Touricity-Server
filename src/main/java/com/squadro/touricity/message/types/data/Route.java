package com.squadro.touricity.message.types.data;

import com.squadro.touricity.message.types.IMessage;

public class Route implements IRoute {

	private String creator;
	private String route_id;
	private IEntry[] entries;
	private String cityId;
	private String title;
	private int privacy;
	
	public Route(){
		//do nothing
	}

	public Route(String creator, String route_id, IEntry[] entries){
		this.creator = creator;
		this.route_id = route_id;
		this.entries = entries;
	}

	public Route(String creator, String route_id, IEntry[] entries, String cityId, String title, int privacy){
		this.creator = creator;
		this.route_id = route_id;
		this.entries = entries;
		this.cityId = cityId;
		this.title = title;
		this.privacy = privacy;
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

	public String getCity_id() {
		return cityId;
	}

	public String getTitle() {
		return title;
	}

	public int getPrivacy() {
		return privacy;
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

	public void setCity_id(String cityId) {
		this.cityId = cityId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPrivacy(int privacy) {
		this.privacy = privacy;
	}
}
