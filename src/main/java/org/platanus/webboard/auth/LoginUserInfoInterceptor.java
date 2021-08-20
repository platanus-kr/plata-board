package org.platanus.webboard.auth;

import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.auth.utils.SessionConst;
import org.platanus.webboard.web.login.dto.UserSessionDto;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginUserInfoInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String requestUserAgent = request.getHeader("user-agent");
        String requestIp = request.getRemoteAddr();
        HttpSession session = request.getSession();
        UserSessionDto userSessionDto = (UserSessionDto) session.getAttribute(SessionConst.LOGIN_USER);
        if (session == null || userSessionDto == null)
            return false;
//        request.setAttribute("user",
//                userSessionDto.from(userService.findById(userSessionDto.getId()), requestUserAgent, requestIp));
        return true;
    }
}
