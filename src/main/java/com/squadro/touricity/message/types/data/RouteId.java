package com.squadro.touricity.message.types.data;

public class RouteId implements IRouteId{

	private String route_id;

	public RouteId(){

	}

	@Override
	public void setRoute_id(String route_id) {
		this.route_id = route_id;
	}

	@Override
	public String getRoute_id() {
		return route_id;
	}
}
