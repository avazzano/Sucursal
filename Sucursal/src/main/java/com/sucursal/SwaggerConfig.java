package com.sucursal;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	// @Value("${info.build.version}")
	private String version = "0.1";

	private String env = "Desarrollo"; // AppProperties.getAmbiente();

	@Bean
	public Docket postsApi() {

		// Adding Header
		ParameterBuilder aParameterBuilder = new ParameterBuilder();
		aParameterBuilder.name("Authorization") // name of header
				.modelRef(new ModelRef("string")).parameterType("header") // type - header
				.required(false) // for compulsory
				.build();
		
		List<springfox.documentation.service.Parameter> aParameters = new ArrayList<>();
		aParameters.add(aParameterBuilder.build()); // add parameter

		return new Docket(DocumentationType.SWAGGER_2).groupName("public-api").
					apiInfo(apiInfo())
					.select()
					.paths(postPaths()).
					build()
				    .globalOperationParameters(aParameters);
	}

	/**
	 * 
	 * @return
	 */
	private Predicate<String> postPaths() {
		return or(regex("/api/sucursal.*"));
	}
	
	


	/**
	 * 
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Sucursal API (" + env + ")").description("v " + version)
				.termsOfServiceUrl("http://")
				.version(version).build();
	}

	

}