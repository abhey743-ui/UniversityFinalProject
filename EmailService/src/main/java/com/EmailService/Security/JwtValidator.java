package com.EmailService.Security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtValidator {

    private final SecretKey secretKey = Keys.hmacShaKeyFor("hhfdsjkfhskjfhewkhfdskllncsklfjwkl93572095532%#$53".getBytes());

    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
