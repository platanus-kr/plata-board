package org.platanus.webboard.web.admin;

import org.platanus.webboard.auth.utils.SessionConst;
import org.platanus.webboard.domain.UserRole;
import org.platanus.webboard.web.login.dto.UserSessionDto;
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
        UserSessionDto userSessionDto = (UserSessionDto) session.getAttribute(SessionConst.LOGIN_USER);
        if (session != null && userSessionDto != null) {
            if (userSessionDto.getRole() == UserRole.ADMIN) {
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
