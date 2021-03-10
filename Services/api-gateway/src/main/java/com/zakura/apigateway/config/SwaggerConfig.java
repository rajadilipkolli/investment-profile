package com.zakura.apigateway.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket api() {

		// return new
		// Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any
		// ()) .paths(PathSelectors.any()).build().apiInfo(apiInfo());

		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.zakura.apigateway.controllers"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());

		/*
		 * return new
		 * Docket(DocumentationType.SWAGGER_2).select().paths(regex("/restservices/.*"))
		 * .build().apiInfo(apiInfo());
		 */
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("API Gateway", "All request to other microservices goes through this API", null, null, null,
				null, null, Collections.EMPTY_LIST);
	}

}
