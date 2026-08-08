package com.example.autonaj2.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

// Servis zaduzen za generisanje i validaciju JWT tokena
@Component
public class JwtService {

    private final SecretKey kljuc;
    private final long trajanjeMs;

    public JwtService(@Value("${jwt.secret}") String secret, @Value("${jwt.trajanje-ms}") long trajanjeMs) {
        this.kljuc = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.trajanjeMs = trajanjeMs;
    }

    public String generisiToken(String email, String uloga) {
        Date sada = new Date();
        Date istice = new Date(sada.getTime() + trajanjeMs);
        return Jwts.builder()
                .subject(email)
                .claim("uloga", uloga)
                .issuedAt(sada)
                .expiration(istice)
                .signWith(kljuc)
                .compact();
    }

    public String izvuciEmail(String token) {
        return parsirajClaims(token).getSubject();
    }

    public String izvuciUlogu(String token) {
        return parsirajClaims(token).get("uloga", String.class);
    }

    public boolean jeValidan(String token) {
        try {
            Claims claims = parsirajClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims parsirajClaims(String token) {
        return Jwts.parser()
                .verifyWith(kljuc)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
