package com.squadro.touricity.controller;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.message.types.IMessage;
import com.squadro.touricity.message.types.data.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
	@RequestMapping(
			value = "/create/comment",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
	)
	@ResponseBody
	public IMessage insertComment(
			@RequestBody CommentRegister commentRegister,
			@CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
	) {
		return Database.insertComment(commentRegister);
	}

	@RequestMapping(
			value = "/get/comment",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
	)
	@ResponseBody
	public CommentRegisterList getComment(
			@RequestBody RouteId routeId,
			@CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
	) {
		return Database.getComment(routeId);
	}
}

