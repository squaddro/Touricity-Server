package com.squadro.touricity.controller.mock;

import com.squadro.touricity.message.types.IMessage;
import com.squadro.touricity.message.types.data.Location;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {

    @RequestMapping(value = "/mock/location", produces = MediaType.APPLICATION_JSON_VALUE)
    public IMessage location(@RequestParam(value="random", defaultValue="false") String random) {
        String locationId = "";
        double latitude = 0;
        double longitude = 0;

        if (random.equals("false")) {
            locationId = "5c0ca3bb-638d-41ef-8e36-53a5b113d044";
            latitude =  39.936;
            longitude = 32.856;
        } else if (random.equals("true")) {
            locationId = RandomGenerator.randomAlphaNumericGenerator(36);
            latitude = RandomGenerator.randomDoubleGenerator(35,40);
            longitude = RandomGenerator.randomDoubleGenerator(30,35);
        }
        return new Location(locationId, latitude, longitude);
    }
}
