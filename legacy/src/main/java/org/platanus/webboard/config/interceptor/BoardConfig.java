package org.platanus.webboard.config.interceptor;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.controller.board.BoardService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class BoardConfig implements WebMvcConfigurer {
    private final BoardService boardService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BoardListInterceptor(boardService))
                .order(11)
                .addPathPatterns("/**");
    }
}
