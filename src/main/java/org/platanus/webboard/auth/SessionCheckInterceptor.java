package org.platanus.webboard.auth;

import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.auth.utils.SessionConst;
import org.platanus.webboard.web.login.dto.UserSessionDto;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class SessionCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String requestUserAgent = request.getHeader("user-agent");
        String requestIp = request.getRemoteAddr();
        HttpSession session = request.getSession();
        UserSessionDto userSessionDto = (UserSessionDto) session.getAttribute(SessionConst.LOGIN_USER);
        if (session != null && userSessionDto != null) {
            if (!userSessionDto.getUserIp().equals(requestIp)) {
                log.info("Interceptor detected by session IP non-equal : #{} {}",
                        userSessionDto.getId(), userSessionDto.getUsername());
                session.invalidate();
                response.sendRedirect("/session_error");
                return false;
            }
            if (!userSessionDto.getUserAgent().equals(requestUserAgent)) {
                log.info("Interceptor detected by session User-Agent non-equal : #{} {}",
                        userSessionDto.getId(), userSessionDto.getUsername());
                session.invalidate();
                response.sendRedirect("/session_error");
                return false;
            }
        }
        return true;
    }
}
