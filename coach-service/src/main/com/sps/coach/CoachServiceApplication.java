package com.sps.coach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Main Application Class for Coach Management Microservice
 *
 * @author SPS Cricket Club
 * @version 1.0.0
 */
@SpringBootApplication
public class CoachServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoachServiceApplication.class, args);
		System.out.println("========================================");
		System.out.println("Coach Management Service Started!");
		System.out.println("Running on: http://localhost:8081");
		System.out.println("API Endpoint: http://localhost:8081/api/coaches");
		System.out.println("========================================");
	}

	/**
	 * Configure CORS to allow frontend access
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:3000")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
						.allowedHeaders("*")
						.allowCredentials(true);
			}
		};
	}
}
