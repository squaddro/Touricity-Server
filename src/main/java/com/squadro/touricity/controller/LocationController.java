package com.squadro.touricity.controller;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.message.types.IMessage;
import com.squadro.touricity.message.types.data.Location;
import com.squadro.touricity.message.types.data.LocationId;
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
		return Database.createLocation(location);
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

