package com.squadro.touricity.controller.mock;

import com.squadro.touricity.message.types.IMessage;
import com.squadro.touricity.message.types.data.Location;
import com.squadro.touricity.message.types.data.Stop;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockStopController {

    @RequestMapping(value = "/mock/stop", produces = MediaType.APPLICATION_JSON_VALUE)
    public IMessage stop(@RequestParam(value="random", defaultValue="false") String random) {
        String stopId = "";
        String locationId = "";
        int expense = 0;
        int duration = 0;
        String comment = "";
        if (random.equals("false")) {
            stopId = "c09fc4c0-cee5-4ce7-bf35-448917e7d8e9";
            locationId = "5c0ca3bb-638d-41ef-8e36-53a5b113d044";
            expense = 4;
            duration = 360;
            comment = "Very nice place!";
        } else if (random.equals("true")) {
            stopId = MockRandomGenerator.randomAlphaNumericGenerator(36);
            locationId = MockRandomGenerator.randomAlphaNumericGenerator(36);
            expense = MockRandomGenerator.randomIntGenerator(5);
            duration = MockRandomGenerator.randomIntGenerator(360);
            comment = MockRandomGenerator.randomAlphaNumericGenerator(1000);
        }
        return new Stop(new Location(), stopId, expense, duration, comment);
    }
}
