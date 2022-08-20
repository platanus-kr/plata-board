package org.platanus.webboard.config.interceptor;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.config.property.PropertyEnvironment;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class CORSConfig implements WebMvcConfigurer {

    public final PropertyEnvironment propertyEnvironment;

    /**
     * 프론트엔드를 위한 CORS 허용 (react, 3000)<br />
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(propertyEnvironment.getFrontendAddress());
    }
}
