package com.squadro.touricity.controller;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.message.types.IMessage;
import com.squadro.touricity.message.types.data.Filter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FilterSearchController {
	@RequestMapping(
			value = "/filter",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
	)
	@ResponseBody
	public IMessage filter(
			@RequestBody Filter filter,
			@CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
	) {
		return Database.getRouteIdsFromFilter(filter);
	}
}
