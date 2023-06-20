package org.platanus.webboard.config.interceptor;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.controller.user.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class UserInfoConfig implements WebMvcConfigurer {
    private final UserService userService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInfoInterceptor(userService))
                .order(10)
                .addPathPatterns("/**");
    }
}
