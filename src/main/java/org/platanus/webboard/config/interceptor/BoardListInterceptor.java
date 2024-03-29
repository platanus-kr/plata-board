package org.platanus.webboard.config.interceptor;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.controller.board.BoardService;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class BoardListInterceptor implements HandlerInterceptor {
    private final BoardService boardService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        request.setAttribute("boardList", boardService.findAll());
        return true;
    }
}
