package com.squadro.touricity.controller;

import com.squadro.touricity.message.types.Greeting;
import com.squadro.touricity.message.types.IMessage;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    @RequestMapping(value = "/greeting", produces = MediaType.APPLICATION_JSON_VALUE)
    public IMessage greeting(@RequestParam(value="name", defaultValue="Mark") String name) {
        String content = "Oh hi, " + name + "! Here is your joke as a json object!";
        return new Greeting(content, "Oh sorry there is no joke!");
    }

    @RequestMapping(value= "/joke", produces = MediaType.APPLICATION_JSON_VALUE)
    public IMessage joke() {
        return new Greeting("this is", "the joke");
    }
}
