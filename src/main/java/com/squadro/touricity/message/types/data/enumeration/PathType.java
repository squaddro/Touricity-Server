package com.squadro.touricity.message.types.data.enumeration;

public enum PathType{
	WALKING(0),
	BUS(1),
	DRIVING(2);

	int value;
	PathType(int i){
		value = i;
	}

	public int getValue(){
		return value;
	}
}