package com.example.backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "https://ewa-front-end-ugsl.onrender.com") // Allow requests from both origins
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Define the allowed HTTP methods
                .allowedHeaders("*"); // Allow all headers (you can customize this as needed)
    }

}