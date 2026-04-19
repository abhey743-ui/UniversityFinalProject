package com.AuthService.FeignClient.FeignClientUtilities;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


import java.util.Date;

public class JwtBuilderUtil {

    public static String createServiceToken(String serviceName, String secret) {

        long now = System.currentTimeMillis();
        long expiry = now + (1000 * 60 *5);

        return Jwts.builder()
                .setSubject(serviceName)
                .claim("type", "INTERNAL-SERVICE")
                .claim("serviceName", serviceName)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expiry))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();

    }
}
