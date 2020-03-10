package com.squadro.touricity.controller;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.database.query.locationQueries.InsertNewLocationQuery;
import com.squadro.touricity.message.types.IMessage;
import com.squadro.touricity.message.types.Status;
import com.squadro.touricity.message.types.data.Location;
import com.squadro.touricity.message.types.data.LocationId;
import com.squadro.touricity.message.types.data.enumeration.StatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class LocationController {
	@RequestMapping(
			value = "/create/location",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
	)
	@ResponseBody
	public IMessage createLocation(
			@RequestBody Location location,
			@CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
	) {
		if(location.getCity_id() == null || location.getLocation_id() == null)
			return Status.build(StatusCode.INSERT_LOCATION_FAIL).edit("You need to set location id and city id!");

		InsertNewLocationQuery locationQuery = new InsertNewLocationQuery(location);
		Database.execute(locationQuery);

		if(locationQuery.isSuccessful())
			return Status.build(StatusCode.INSERT_LOCATION_SUCCESSFULL);
		else if(!locationQuery.isCityExists())
			return Status.build(StatusCode.CITY_DOES_NOT_EXISTS);
		else if(!locationQuery.isLocationExists())
			return Status.build(StatusCode.LOCATION_DOES_NOT_EXISTS);
		else
			return Status.build(StatusCode.INSERT_LOCATION_FAIL);
	}

	@RequestMapping(
			value = "/location/info",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
	)
	@ResponseBody
	public IMessage getLocationInfo(
			@RequestBody LocationId location_id,
			@CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
	) {
		Location location = null;
		location = Database.getLocationInfo(location_id.getLocation_id());
		return location;
	}
}

