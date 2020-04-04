package com.squadro.touricity.message.types.data;

import com.squadro.touricity.message.types.IMessage;

import java.util.List;

public class FilterResult implements IFilterResult {

	private List<RouteLike> routeList;

	public FilterResult(List<RouteLike> routeList) {
		this.routeList = routeList;
	}

	public List<RouteLike> getRouteList() {
		return routeList;
	}

	public void setRouteList(List<RouteLike> routeList) {
		this.routeList = routeList;
	}
}
