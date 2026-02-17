package com.hrms.backend.config;

import com.hrms.backend.filters.ExceptionFilter;
import com.hrms.backend.filters.JwtAuthfilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
    @Autowired
    ExceptionFilter exceptionFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable() // disable CSRF for simplicity, enable in production if needed
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/auth/login").permitAll() // allow login endpoint
                        .requestMatchers("/swagger-ui/**").permitAll()// allow Swagger UI
                                .requestMatchers("/resource/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/game-types").hasAuthority("HR")// allow OpenAPI.requestMatchers()
                                .requestMatchers(HttpMethod.PUT,"/game-types").hasAuthority("HR")// allow OpenAPI.requestMatchers()
                        .requestMatchers( "/hr/posts/delete-unappropriate").hasAuthority("HR")
//                        .requestMatchers("/employee/**").hasAuthority("Employee")
                        .anyRequest().authenticated()
                )
                // secure all other endpoints
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(exceptionFilter,UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
