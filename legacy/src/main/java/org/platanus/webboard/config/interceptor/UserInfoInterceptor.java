package org.platanus.webboard.config.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.controller.login.dto.UserSessionDto;
import org.platanus.webboard.controller.user.UserService;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
public class UserInfoInterceptor implements HandlerInterceptor {
    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String requestUserAgent = request.getHeader("user-agent");
        String requestIp = request.getRemoteAddr();
        HttpSession session = request.getSession();
        UserSessionDto userSessionDto = (UserSessionDto) session.getAttribute(SessionConst.LOGIN_USER_INTERCEPTOR);
        if (session != null && userSessionDto != null) {
            request.setAttribute("user",
                    userSessionDto.from(userService.findById(userSessionDto.getId()), requestUserAgent, requestIp));
        }
        return true;
    }
}
