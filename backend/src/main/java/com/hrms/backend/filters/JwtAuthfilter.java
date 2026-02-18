package com.hrms.backend.filters;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.exceptions.JwtTokenRequired;
import com.hrms.backend.services.AuthServices.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthfilter extends OncePerRequestFilter{

    private final JwtService _jwtService;

    @Autowired
    public JwtAuthfilter(JwtService jwtService) {
        _jwtService = jwtService;
    }
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;
        JwtInfoDto jwtInfo = null;
        StringBuffer url = request.getRequestURL();
        if(authHeader == null && url.indexOf("login")==-1 && url.indexOf("resource") == -1 && url.indexOf("swagger") == -1 && url.indexOf("/v3/api-docs") == -1 ){
            throw new JwtTokenRequired();
        }
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            email = _jwtService.extractEmail(token);
            jwtInfo = _jwtService.getJwtInfo(token);
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (_jwtService.validateToken(token, jwtInfo)) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(jwtInfo.getRoleTitle());
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        jwtInfo,
                        null,
                        Collections.singletonList(authority)
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}
