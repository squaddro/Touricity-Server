package com.squadro.touricity.controller.mock;

import com.squadro.touricity.message.types.IMessage;
import com.squadro.touricity.message.types.data.*;
import com.squadro.touricity.message.types.data.enumeration.PathType;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockRouteController {

    @RequestMapping(value = "/mock/route", produces = MediaType.APPLICATION_JSON_VALUE)
    public IMessage route(@RequestParam(value="random", defaultValue="false") String random) {
        String creator = "";
        String routeId = "";
        IEntry[] entries = null;
        Stop stop1, stop2, stop3;
        Path.PathVertex v1, v2, v3, v4;
        Path.PathVertex[] vertices1, vertices2;
        Path path1, path2;
        if (random.equals("false")) {
            creator = "4c0ac9c5-ecf7-bf57-ce21-175587e8d8b6";
            routeId = "c08ac5c2-5b9f-6a8f-35bf-448917e7d8e9";
            stop1 = new Stop(new Location(), "c09fc4c0-863e-4ce7-bf35-37e5b113d044", 3, 360, "Very nice place!");
            stop2 = new Stop(new Location(), "82ed63bc-cee5-4ce7-cab7-448917e7d8e9", 2, 360, "Very nice place!");
            stop3 = new Stop(new Location(), "c09fc4c0-638d-e8e6-bf35-53a5b113d044", 3, 360, "Very nice place!");
            v1 = new Path.PathVertex(39.936 ,32.860);
            v2 = new Path.PathVertex(39.938,32.862);
            v3 = new Path.PathVertex(39.940,32.864);
            v4 = new Path.PathVertex(39.942,32.866);
            vertices1 = new Path.PathVertex[]{v1,v2};
            vertices2 = new Path.PathVertex[]{v3,v4};
            path1 = new Path("d28cf3e8-dbe8-4ce7-de53-448917e7d8e9" , PathType.values()[1], vertices1, 360, 3, "Very nice place!");
            path2 = new Path("e37de228-de53-c3a5-edb7-427634d87ea8" , PathType.values()[2], vertices2, 330, 1, "Very nice place!");
            entries = new IEntry[]{stop1,path1,stop2,path2,stop3};
        } else if (random.equals("true")) {
            creator = MockRandomGenerator.randomAlphaNumericGenerator(36);
            routeId = MockRandomGenerator.randomAlphaNumericGenerator(36);
            stop1 = new Stop(new Location(), MockRandomGenerator.randomAlphaNumericGenerator(36), MockRandomGenerator.randomIntGenerator(5), MockRandomGenerator.randomIntGenerator(360), MockRandomGenerator.randomAlphaNumericGenerator(1000));
            stop2 = new Stop(new Location(), MockRandomGenerator.randomAlphaNumericGenerator(36), MockRandomGenerator.randomIntGenerator(5), MockRandomGenerator.randomIntGenerator(360), MockRandomGenerator.randomAlphaNumericGenerator(1000));
            stop3 = new Stop(new Location(), MockRandomGenerator.randomAlphaNumericGenerator(36), MockRandomGenerator.randomIntGenerator(5), MockRandomGenerator.randomIntGenerator(360), MockRandomGenerator.randomAlphaNumericGenerator(1000));
            v1 = new Path.PathVertex(MockRandomGenerator.randomDoubleGenerator(35,40), MockRandomGenerator.randomDoubleGenerator(30,35));
            v2 = new Path.PathVertex(MockRandomGenerator.randomDoubleGenerator(35,40), MockRandomGenerator.randomDoubleGenerator(30,35));
            v3 = new Path.PathVertex(MockRandomGenerator.randomDoubleGenerator(35,40), MockRandomGenerator.randomDoubleGenerator(30,35));
            v4 = new Path.PathVertex(MockRandomGenerator.randomDoubleGenerator(35,40), MockRandomGenerator.randomDoubleGenerator(30,35));
            vertices1 = new Path.PathVertex[]{v1,v2};
            vertices2 = new Path.PathVertex[]{v3,v4};
            path1 = new Path(MockRandomGenerator.randomAlphaNumericGenerator(36) , PathType.values()[MockRandomGenerator.randomIntGenerator(3)], vertices1, MockRandomGenerator.randomIntGenerator(360), MockRandomGenerator.randomIntGenerator(5), MockRandomGenerator.randomAlphaNumericGenerator(1000));
            path2 = new Path(MockRandomGenerator.randomAlphaNumericGenerator(36) , PathType.values()[MockRandomGenerator.randomIntGenerator(3)], vertices2, MockRandomGenerator.randomIntGenerator(360), MockRandomGenerator.randomIntGenerator(5), MockRandomGenerator.randomAlphaNumericGenerator(1000));
            entries = new IEntry[]{stop1,path1,stop2,path2,stop3};
        }
        return new Route(creator, routeId, entries);
    }
}
