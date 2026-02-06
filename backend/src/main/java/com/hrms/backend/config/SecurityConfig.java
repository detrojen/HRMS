package com.hrms.backend.config;

import com.hrms.backend.filters.JwtAuthfilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    JwtAuthfilter jwtAuthFilter;
//    @Autowired
//    FilterChainExceptionHandler filterChainExceptionHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable() // disable CSRF for simplicity, enable in production if needed
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/auth/login").permitAll() // allow login endpoint
                        .requestMatchers("/swagger-ui/**").permitAll() // allow Swagger UI
                        .requestMatchers("/v3/api-docs/**").permitAll()// allow OpenAPI.requestMatchers()
                        .anyRequest().authenticated()
                )
                // secure all other endpoints
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Add JWT filter before Spring Security's default filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
