package com.ProductService.SecurityService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;


@Component
public class TokenVerification {

    String secretKey = "7493ndddsjmkjalsdjlajdsaJDIOWUROIWDJWKXSNMLKU";

    String InternalSecretKey = "dsjhskafhwkjsjf11332523hdsfkfslkj@1klrjwlrjwljwle";


    String BasketInterToken = "JDFHSKFHSKLFJASKLFHSDKLHFS9352305725207-5-37@@@@NMDFSKLFJLFJDSLDF";

    private  SecretKey getSecretKey() {

        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    }
    private SecretKey getInternalSecretKey(){
        return Keys.hmacShaKeyFor(InternalSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    private SecretKey getBasketInternalKey(){
         return Keys.hmacShaKeyFor(BasketInterToken.getBytes(StandardCharsets.UTF_8));
    }

    public Claims verification(String token ){
        try {
            return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody();

        } catch (RuntimeException e) {
            return null;
        }
    }

    public Claims internalVerification(String token ){
             try{

                 return Jwts.parserBuilder().setSigningKey(getInternalSecretKey()).build()
                         .parseClaimsJws(token).getBody();
             } catch (RuntimeException e) {
                 return null;
             }
    }
    public Claims BasketInterVerificationToken(String token){
                  try{

                      return Jwts.parserBuilder().setSigningKey(getBasketInternalKey()).build().parseClaimsJws(token).getBody();
                  } catch (RuntimeException e) {
                      return null;
                  }
    }
}
