package com.hana.hanalink.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.access-expired-ms}")
    private Long accessExpiredMS;
    @Value("${jwt.claims.auth-key}")
    private String authKey;

    public String createToken(Claims claims, String secretKey, Long expiredMs) {
        long now = new Date().getTime();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(now + expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .setHeaderParam("typ", "JWT")
                .compact();
    }

    public Boolean isExpired(String token) {
        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    public String generateAccessToken(Object value) {
        Claims claims = Jwts.claims();
        claims.put(authKey, value);

        return createToken(
                claims,
                secretKey,
                accessExpiredMS
        );
    }

    public <T> T getAuthValue(String token, Class<T> clazz) {
        Claims claims = parseToClaims(token);
        return claims.get(authKey, clazz);
    }

    private Claims parseToClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token).getBody();
    }
}