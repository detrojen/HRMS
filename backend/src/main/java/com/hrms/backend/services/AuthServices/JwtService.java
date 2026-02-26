package com.hrms.backend.services.AuthServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    private static final Log log = LogFactory.getLog(JwtService.class);

    public static final  String SECRET = "5367566859703373367639792F423F452848284D6251655468576D5A71347437";

    public String generateToken(Long userId,String email,String role) {
        log.info(SECRET);
        Map<String, Object> claims = new HashMap<>();
        claims.put("roleTitle",role);
        claims.put("userId",userId);
        claims.put("email",email);
        return createToken(claims,email);
    }

    private String createToken(Map<String, Object> claims,String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public JwtInfoDto getJwtInfo(String tocken){
        Claims claims = extractAllClaims(tocken);
        Long userId = claims.get("userId", Long.class);
        return new JwtInfoDto(userId,claims.get("email").toString(),claims.get("roleTitle").toString());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, JwtInfoDto jwtInfoDto) {
        final String email = extractEmail(token);
        return (email.equals(jwtInfoDto.getEmail()) && !isTokenExpired(token));
    }
}
