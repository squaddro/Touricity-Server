package com.squadro.touricity.message.types.data;

import java.util.List;

public interface IFilterResult extends IDataType{

	List<Route> getRouteList();

	void setRouteList(List<Route> routeList);
}
