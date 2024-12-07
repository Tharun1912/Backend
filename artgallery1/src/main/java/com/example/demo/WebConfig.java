package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Adjust the path if necessary
                .allowedOrigins("http://localhost:3000")  // Allow frontend origin
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Add other methods if needed
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
