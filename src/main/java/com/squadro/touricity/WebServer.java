package com.squadro.touricity;

import com.squadro.touricity.session.RequestInterceptor;
import com.squadro.touricity.database.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class WebServer {
	private static final String[] origins = {"https://touricity.herokuapp.com", "http://touricity.herokuapp.com", "http://localhost:8080"};
	
	public static void main(String[] args){
		SpringApplication.run(WebServer.class, args);
		Database.checkConnection();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Autowired
			RequestInterceptor requestInterceptor;

			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(origins).allowCredentials(true).maxAge(3600);
			}

			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(requestInterceptor);
			}
		};
	}
}