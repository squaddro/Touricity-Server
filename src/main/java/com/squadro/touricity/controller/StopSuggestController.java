package com.squadro.touricity.controller;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.message.types.data.Suggestion;
import com.squadro.touricity.message.types.data.BoundPoints;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
public class StopSuggestController {
    @RequestMapping(
            value = "/suggest",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Suggestion suggestStops(
            @RequestBody BoundPoints boundPoints,
            @CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
    ) {
        return Database.suggestStops(boundPoints);
    }

}
