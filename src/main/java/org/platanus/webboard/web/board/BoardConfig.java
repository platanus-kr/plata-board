package org.platanus.webboard.web.board;

import lombok.RequiredArgsConstructor;
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
                .order(10)
                .addPathPatterns("/**");
    }
}
