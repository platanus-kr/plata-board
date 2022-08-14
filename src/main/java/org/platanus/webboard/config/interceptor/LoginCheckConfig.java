package org.platanus.webboard.config.interceptor;

import org.platanus.webboard.controller.login.argumentresolver.LoginArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class LoginCheckConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/",
                        "/board/*",
                        "/board",
                        "/article/*",
                        "/user/join",
                        "/login",
                        "/logout",
                        "/css/**",
                        "/*.ico",
                        "/error",
                        "/session_error",
                        "/api/**"
                );
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginArgumentResolver());
    }
}
