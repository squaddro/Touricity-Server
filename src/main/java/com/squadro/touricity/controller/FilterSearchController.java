package com.squadro.touricity.controller;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.message.types.Status;
import com.squadro.touricity.message.types.data.Filter;
import com.squadro.touricity.message.types.data.Route;
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
    public List<String> filter(
            @RequestBody Filter filter,
            @CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
    ) {
        List<String> routeIdsFromFilter = Database.getRouteIdsFromFilter(filter);
        return routeIdsFromFilter;
        //Later, when getting route from route_id method is implemented, this request will return
        //list of routes,for now it returns list of route_ids.
    }
}
