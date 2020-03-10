package com.squadro.touricity.controller;

import com.squadro.touricity.database.Database;
import com.squadro.touricity.message.types.IMessage;
import com.squadro.touricity.message.types.data.Credential;
import com.squadro.touricity.session.SessionCookie;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RestController
public class UserController {

    @RequestMapping(
            value = "/signup",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public IMessage signUp(
     @RequestBody Credential credential,
     @CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
    ){
        IMessage msg = null;
        msg = Database.signUp(cookie,credential);
        return msg;
    }

    @RequestMapping(
            value = "/signin",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public IMessage signIn(
            @RequestBody Credential credential,
            @CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
    ){
        IMessage msg = null;
        msg = Database.signIn(cookie,credential);
        return msg;
    }

    @RequestMapping(
            value = "/signout",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public IMessage signOut(
            @RequestBody Credential credential,
            @CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
    ){
        IMessage msg = null;
        msg = Database.signOut(cookie,credential);
        return msg;
    }
}
