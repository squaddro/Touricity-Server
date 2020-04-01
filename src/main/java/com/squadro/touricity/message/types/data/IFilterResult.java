package com.squadro.touricity.message.types.data;

import java.util.List;

public interface IFilterResult extends IDataType{

	List<RouteLike> getRouteList();

	void setRouteList(List<RouteLike> routeList);
}
