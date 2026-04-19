package com.AuthService.Security.SecurityUtils;

import com.AuthService.Dto.UserInfoForToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;



@Component
public class JwtToken {

    String secretKey = "7493ndddsjmkjalsdjlajdsaJDIOWUROIWDJWKXSNMLKU";

    public SecretKey getSecretKey(){

        return  Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    }
          public String getToken(UserInfoForToken user){
          return Jwts.builder().setSubject(user.getUserName())
                .setExpiration(new Date(System.currentTimeMillis() * 100000))
                .setIssuedAt(new Date())
               .claim("id",user.getId())
                .signWith(getSecretKey())
                .compact();
   }

}
