package com.zakura.portfolioservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket api() {

		// return new
		// Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any
		// ()) .paths(PathSelectors.any()).build().apiInfo(apiInfo());

		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.zakura.portfolioservice.controller"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());

		/*
		 * return new
		 * Docket(DocumentationType.SWAGGER_2).select().paths(regex("/restservices/.*"))
		 * .build().apiInfo(apiInfo());
		 */
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Portfolio Manager", "App to manage Portfolio", null, null, null, null, null,
				Collections.emptyList());
	}

}
