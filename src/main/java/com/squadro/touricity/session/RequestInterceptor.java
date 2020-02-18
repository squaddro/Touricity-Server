package com.squadro.touricity.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        logger.info("Request URL::" + request.getRequestURL().toString());
        request.setAttribute("startTime", startTime);
        SessionCookie cookie = extractCookie(request.getCookies());
        logger.info("Received cookie: " + cookie);

        if(cookie != null) {
            AccountCheckQuery query = new AccountCheckQuery(cookie);
            query.execute();

            if(!query.isExists()) {
                SessionCookie session = SessionCookie.random();
                response.addCookie(new Cookie(SessionCookie.TAG, session.getUuid()));
                CreateAccountQuery createQuery = new CreateAccountQuery(session);
                createQuery.execute();
            }
        }

        //if returned false, we need to make sure 'response' is sent
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        //logger.info("Request URL::" + request.getRequestURL().toString()
        //        + " Sent to Handler :: Current Time=" + System.currentTimeMillis());
        //we can add attributes in the modelAndView and use that in the view page
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        //long startTime = (Long) request.getAttribute("startTime");
        //logger.debug("Request URL::" + request.getRequestURL().toString()
        //       + ":: End Time=" + System.currentTimeMillis());
        //logger.debug("Request URL::" + request.getRequestURL().toString()
        //        + ":: Time Taken=" + (System.currentTimeMillis() - startTime));
    }

    private SessionCookie extractCookie(Cookie[] cookies) {
        if(cookies != null){
            for(Cookie cookie : cookies) {
                SessionCookie sc = SessionCookie.from(cookie);
                if(sc != null)
                    return sc;
            }
        }
        return null;
    }
}
