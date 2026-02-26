package com.hrms.backend.filters;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.exceptions.JwtTokenRequired;
import com.hrms.backend.services.AuthServices.AuthService;
import com.hrms.backend.services.AuthServices.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@Component
public class JwtAuthfilter extends OncePerRequestFilter{

    private final JwtService _jwtService;
    private final AuthService authService;
    @Autowired
    public JwtAuthfilter(JwtService jwtService,AuthService authService) {
        _jwtService = jwtService;
        this.authService = authService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String refreshToken = null;
        String token = null;
        String email = null;
        JwtInfoDto jwtInfo = null;
        StringBuffer url = request.getRequestURL();

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("HRMS_REFRESH_TOKEN".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        if(  authHeader == null && url.indexOf("login")==-1 && url.indexOf("resource") == -1 && url.indexOf("swagger") == -1 && url.indexOf("/v3/api-docs") == -1 ){
            throw new JwtTokenRequired();
        }
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            try {
                email = _jwtService.extractEmail(token);
                jwtInfo = _jwtService.getJwtInfo(token);
            }catch (ExpiredJwtException e){
                if(refreshToken!= null && !_jwtService.isTokenExpired(refreshToken)){
                    email = _jwtService.extractEmail(refreshToken);
                    token = authService.login(email);
                    response.addHeader(HttpHeaders.AUTHORIZATION,token);
                }else{
                    throw e;
                }

            }finally {
                email = _jwtService.extractEmail(token);
                jwtInfo = _jwtService.getJwtInfo(token);
            }
        }




        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (token!=null) {
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
