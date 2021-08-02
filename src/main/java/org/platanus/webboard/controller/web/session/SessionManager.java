package org.platanus.webboard.controller.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "mySessionId";
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    public void createSession(Object value, HttpServletResponse response) {
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);

    }

    public Object getSession(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return new IllegalArgumentException("없는 정보 입니다.");
        }
        for (Cookie c : cookies) {
            if (c.getName().equals(SESSION_COOKIE_NAME)) {
                return sessionStore.get(c.getValue());
            }
        }
        return null;
    }

    public void expire(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new IllegalArgumentException("없는 정보 입니다.");
        }
        for (Cookie c : cookies) {
            if (c.getName().equals(SESSION_COOKIE_NAME)) {
                sessionStore.remove(c.getValue());
            }
        }
    }
}
