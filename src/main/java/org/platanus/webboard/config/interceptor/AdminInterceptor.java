package org.platanus.webboard.config.interceptor;

import org.platanus.webboard.controller.login.dto.UserSessionDto;
import org.platanus.webboard.domain.UserRole;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();
        UserSessionDto userSessionDto = (UserSessionDto) session.getAttribute(SessionConst.LOGIN_USER_INTERCEPTOR);
        if (session != null && userSessionDto != null) {
            if (userSessionDto.getRole() == UserRole.ROLE_ADMIN) {
                return true;
            } else {
                response.sendRedirect("/");
                return false;
            }
        } else {
            response.sendRedirect("/login?redirectURL=" + requestURI);
            return false;
        }
    }
}
