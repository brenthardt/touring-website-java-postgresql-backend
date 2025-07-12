package org.example.barlasvoyage_backend.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String secret = "your-256-bit-secret-your-256-bit-secret";
    @Getter
    private final Key secretKey = Keys.hmacShaKeyFor(secret.getBytes());


    public String generateToken(String phone, String role) {
        long expirationMillis = 86400000;
        return Jwts.builder()
                .setSubject(phone)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey)
                .compact();
    }


}

