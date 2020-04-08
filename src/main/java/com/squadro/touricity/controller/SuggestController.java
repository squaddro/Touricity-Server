package com.squadro.touricity.controller;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.message.types.Suggestion;
import com.squadro.touricity.message.types.data.BoundPoints;
import com.squadro.touricity.message.types.data.Location;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SuggestController {
    @RequestMapping(
            value = "/suggest",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Suggestion suggest(
            @RequestBody BoundPoints boundPoints,
            @CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
    ) {
        return Database.suggest(boundPoints);
    }

}
