package com.TokenCheckService.TokenUtilities;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;


@Component
public class TokenVerification {

    private final String secret_Key = "7493ndddsjmkjalsdjlajdsaJDIOWUROIWDJWKXSNMLKU";

    public SecretKey getKey(){

        return   Keys.hmacShaKeyFor(secret_Key.getBytes(StandardCharsets.UTF_8));

    }

    public Claims verifyToken(String token){
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }

}
