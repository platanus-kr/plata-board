package org.platanus.webboard.config.swagger;

import org.platanus.webboard.config.constant.ConfigConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@Profile({ConfigConstant.PROPERTY_ENV_PROFILE_NOT_PRODUCTION})
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Bean
    public Docket boardApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("REST API v1")
                .select()
                .apis(RequestHandlerSelectors.
                        basePackage("org.platanus.webboard"))
                .paths(PathSelectors.ant("/api/v1/**"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Plata Board",
                "Plata board API Document",
                "version 2.1",
                "https://bbs.canxan.com",
                new Contact("Contact Me", "https://platanus.me", "platanus.kr@gmail.com"),
                "MIT",
                "#",
                new ArrayList<>()
        );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        String baseUrl = StringUtils.trimTrailingCharacter(this.baseUrl, '/');

        registry.
                addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);

//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
