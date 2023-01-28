package com.user.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {
	
	private static final String GET="GET";
	private static final String POST="POST";
	private static final String DELETE="DELETE";
	private static final String PUT="PUT";
	private static final String UPDATE="UPDATE";
	
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
		@Override
		public void addCorsMappings(CorsRegistry registry) {
			 registry.addMapping("/**")//ALLOW ALL REQUESTS
             .allowedMethods(GET, POST, PUT, DELETE,UPDATE)
             .allowedHeaders("*")//ALL KIND OF HEADERS
             .allowedOriginPatterns("*")//
             .allowCredentials(true);
			
		}
		};
	}

}
