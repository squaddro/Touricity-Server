package com.squadro.touricity.message.types.data;

public interface ICity extends IDataType{

	String getCity_Id();
	String getCity_Name();

	void setCity_Id(String cityId);
	void setCity_Name(String cityName);
}
