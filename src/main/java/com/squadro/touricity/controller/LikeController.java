package com.squadro.touricity.controller;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.message.types.IMessage;
import com.squadro.touricity.message.types.data.Like;
import com.squadro.touricity.message.types.data.LikeId;
import com.squadro.touricity.message.types.data.LikeRegister;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class LikeController {
	@RequestMapping(
			value = "/like/info",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
	)
	@ResponseBody
	public IMessage getLikeInfo(
			@RequestBody LikeId like_id,
			@CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
	) {
		Like like = null;
		like = Database.getLikeInfo(like_id.getLike_id());
		return like;
	}

	@RequestMapping(
			value = "/create/like",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
	)
	@ResponseBody
	public IMessage insertLike(
			@RequestBody LikeRegister likeRegister,
			@CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
	) {
		return Database.insertLike(likeRegister);
	}
}

