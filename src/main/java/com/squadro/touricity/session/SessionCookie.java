package com.squadro.touricity.session;

import javax.servlet.http.Cookie;
import java.util.UUID;

public class SessionCookie {
    public static final String TAG = "cookie_uuid";

    private UUID uuid;

    private SessionCookie(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid.toString();
    }

    public static SessionCookie from(Cookie cookie) {
        if(cookie == null)
            return null;

        if(!TAG.equals(cookie.getName()))
            return null;

        return from(cookie.getValue());
    }

    public static SessionCookie from(String uuid) {
        UUID id = UUID.fromString(uuid);

        return from(id);
    }

    public static SessionCookie from(UUID uuid) {
        if(uuid == null)
            return null;

        return new SessionCookie(uuid);
    }

    public static SessionCookie random() {
        return new SessionCookie(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return getUuid();
    }
}
