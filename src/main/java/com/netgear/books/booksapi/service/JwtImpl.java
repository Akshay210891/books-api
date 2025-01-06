package com.netgear.books.booksapi.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

import static io.jsonwebtoken.Jwts.*;

@Component
@Service
public class JwtImpl{
    @Value("${jwt.secret.string}")
    private String secretString;

    private SecretKey generateSigningKey() {
        byte[] bytes = Decoders.BASE64.decode(secretString);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String generateToken(Map<String, Object> claim) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + 1000 * 60 * 60 * 24);

        return builder()
                .claims(claim)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(generateSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(generateSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public String extractSubject(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        return new Date().before(extractExpiration(token));
    }
}
