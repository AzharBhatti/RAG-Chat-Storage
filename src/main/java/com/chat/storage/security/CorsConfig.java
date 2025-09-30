package com.chat.storage.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow only specific origins
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedOrigin("http://localhost:8096");

        // Allow specific HTTP methods (GET, POST, PUT, DELETE)
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");

        // Allow specific headers (e.g., Authorization, Content-Type)
        config.addAllowedHeader("Authorization");
        config.addAllowedHeader("Content-Type");

        // Allow credentials (cookies, authentication)
        config.setAllowCredentials(true);

        // Register CORS configuration for all endpoints
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
