package ProductManagement.example.Security.SecurityUtilities;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtToken {

    private  String Secret_key = "dsjhskafhwkjsjf11332523hdsfkfslkj@1klrjwlrjwljwle";

    public SecretKey getSigningKey(){
       return  Keys.hmacShaKeyFor(Secret_key.getBytes(StandardCharsets.UTF_8));

    }

    public String getToken(String serviceName){

        return Jwts.builder().signWith(getSigningKey())
                .setExpiration(new Date(System.currentTimeMillis() * 1000))
                .setIssuedAt(new Date())
                .setSubject(serviceName)
                .claim("type", "INTERNAL-SERVICE")
                .claim("serviceName", serviceName)
                .signWith(getSigningKey())
                .compact();

    }

}
