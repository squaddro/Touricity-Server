package com.squadro.touricity.controller;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.message.types.IMessage;
import com.squadro.touricity.message.types.data.Route;
import org.slf4j.IMarkerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class RouteController {

    @RequestMapping(
            value = "/route/info",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Route getRouteInfo(
     @RequestBody String route_id,
     @CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
    ){
        Route route = null;
        route = Database.getRouteInfo(route_id);
        return route;
    }

    @RequestMapping(
            value = "/route/delete",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public IMessage deleteRoute(
            @RequestBody String route_id,
            @CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
    ){
        return Database.deleteRoute(route_id);
    }

}
