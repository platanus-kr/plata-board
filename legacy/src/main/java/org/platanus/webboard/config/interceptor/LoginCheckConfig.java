package org.platanus.webboard.config.interceptor;

import org.platanus.webboard.config.constant.ConfigConstant;
import org.platanus.webboard.controller.login.argumentresolver.LoginArgumentResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class LoginCheckConfig implements WebMvcConfigurer {

    @Value("${plataboard.environment.profile}")
    private String profile;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        if (!profile.equals(ConfigConstant.PROPERTY_ENV_PROFILE_PRODUCTION)) {
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
                            "/api/**",
                            "/media/**",
                            "/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swagger-ui.html", "/webjars/**",
                            "/swagger-ui/**"
                    );
        }
        
        if (profile.equals(ConfigConstant.PROPERTY_ENV_PROFILE_PRODUCTION)) {
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
                            "/api/**",
                            "/media/**"
                    );
        }
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginArgumentResolver());
    }
}
