package com.squadro.touricity.message.types.data;

import com.squadro.touricity.message.types.IMessage;

import java.util.List;

public class FilterResult implements IFilterResult {

	private List<Route> routeList;

	public FilterResult(List<Route> routeList) {
		this.routeList = routeList;
	}

	public List<Route> getRouteList() {
		return routeList;
	}

	public void setRouteList(List<Route> routeList) {
		this.routeList = routeList;
	}
}
