package com.chat.storage.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain security(HttpSecurity http) throws Exception {
        http.cors().and()
                .authorizeRequests()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/webjars/**")  // Allow Swagger UI and API docs
                .permitAll()  // Allow unrestricted access to Swagger UI and API docs
                .anyRequest()  // Allow unrestricted access to all other endpoints
                .permitAll()  // Disable authentication for all requests
                .and()
                .csrf().disable();  // Disable CSRF for testing if you're not using stateful sessions (e.g., JWT)

        return http.build();
    }
}
