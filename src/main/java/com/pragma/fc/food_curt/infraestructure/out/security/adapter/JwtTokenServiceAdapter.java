package com.pragma.fc.food_curt.infraestructure.out.security.adapter;

import com.pragma.fc.food_curt.domain.spi.ITokenServicePort;
import com.pragma.fc.food_curt.infraestructure.out.security.entity.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.function.Function;

public class JwtTokenServiceAdapter implements ITokenServicePort {
    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Override
    public boolean isAccessTokenValid(String token) {
        if (!isTokenValidGeneric(token)) return false;
        String tokenType = extractClaim(token, claims -> claims.get("type", String.class));
        return TokenType.ACCESS.name().equals(tokenType);
    }

    @Override
    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimExtractor) {
        Claims claims = extractAllClaims(token);
        return claimExtractor.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenValidGeneric(String token) {
        Date expiration = extractClaim(token, Claims::getExpiration);
        Date notBefore = extractClaim(token, Claims::getNotBefore);
        return (expiration.after(new Date()) && notBefore.before(new Date()));
    }
}
