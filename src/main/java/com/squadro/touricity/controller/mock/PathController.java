package com.squadro.touricity.controller.mock;

import com.squadro.touricity.message.types.data.Path;
import com.squadro.touricity.message.types.data.enumeration.PathType;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PathController {

    @RequestMapping(value = "/mock/path", produces = MediaType.APPLICATION_JSON_VALUE)
    public Path path(@RequestParam(value="random", defaultValue="false") String random) {
        String pathId = "";
        int pathType = 0;
        int expense = 0;
        int duration = 0;
        String comment = "";
        Path.PathVertex[] vertices = null;
        if (random.equals("false")) {
            pathId = "d28cf3e8-dbe8-4ce7-de53-448917e7d8e9";
            pathType = 0;
            expense = 4;
            duration = 360;
            comment = "Very nice place!";
            Path.PathVertex v1 = new Path.PathVertex(39.936 ,32.856);
            Path.PathVertex v2 = new Path.PathVertex(39.938,32.862);
            vertices = new Path.PathVertex[]{v1,v2};
        } else if (random.equals("true")) {
            pathId  = RandomGenerator.randomAlphaNumericGenerator(36);
            pathType = RandomGenerator.randomIntGenerator(3);
            expense = RandomGenerator.randomIntGenerator(5);
            duration = RandomGenerator.randomIntGenerator(360);
            comment = RandomGenerator.randomAlphaNumericGenerator(1000);
            int size = RandomGenerator.randomIntGenerator(6);
            vertices = new Path.PathVertex[size];
            for(int i = 0; i<size; i++){
                Path.PathVertex vertex = new Path.PathVertex(RandomGenerator.randomDoubleGenerator(35,40),RandomGenerator.randomDoubleGenerator(30,35));
                vertices[i] = vertex;
            }
        }
        return new Path(pathId, PathType.values()[pathType], vertices , duration,expense, comment);
    }
}
