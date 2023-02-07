package org.platanus.webboard.config.swagger;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

	@Bean
	public Docket boardApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("REST API v1")
				.select()
				.apis(RequestHandlerSelectors.
						basePackage("org.platanus.webboard.controller.*.rest"))
				.paths(PathSelectors.ant("/api/v1/**"))
				.build()
				.apiInfo(apiInfo());
	}

	//@Bean
	//public Docket apiV2() {
	//	return new Docket(DocumentationType.SWAGGER_2)
	//			.useDefaultResponseMessages(false)
	//			.groupName("groupName2")
	//			.select()
	//			.apis(RequestHandlerSelectors.
	//					basePackage("com.app.edit"))
	//			.paths(PathSelectors.ant("/v2/api/**"))
	//			.build()
	//			.apiInfo(apiInfo());
	//}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
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
}
